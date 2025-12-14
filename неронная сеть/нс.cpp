#include <iostream>
#include <vector>
#include <cmath>
#include <algorithm>
#include <stdexcept>

// Класс Matrix для представления изображений/тензоров
class Matrix {
private:
    int rows, cols;
    std::vector<std::vector<float>> data;

public:
    Matrix() : rows(0), cols(0) {}
    Matrix(int r, int c) : rows(r), cols(c) {
        data.resize(r, std::vector<float>(c, 0.0f));
    }

    int getRows() const { return rows; }
    int getCols() const { return cols; }

    float& operator()(int i, int j) { return data[i][j]; }
    const float& operator()(int i, int j) const { return data[i][j]; }

    void print() const {
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                std::cout << data[i][j] << " ";
            }
            std::cout << std::endl;
        }
    }
};

// Базовый класс Layer
class Layer {
public:
    virtual Matrix forward(const Matrix& input) = 0;
    virtual ~Layer() = default;
};

// Слой свертки (Conv2D)
class Conv2D : public Layer {
private:
    int kernelSize, numFilters;
    std::vector<Matrix> kernels; // 3D: [numFilters][kernelSize][kernelSize]
    Matrix bias;

public:
    Conv2D(int ks, int nf) : kernelSize(ks), numFilters(nf) {
        kernels.resize(numFilters, Matrix(kernelSize, kernelSize));
        bias = Matrix(1, numFilters);
        // Инициализация случайными значениями
        for (int f = 0; f < numFilters; ++f) {
            for (int i = 0; i < kernelSize; ++i) {
                for (int j = 0; j < kernelSize; ++j) {
                    kernels[f](i, j) = (rand() % 100) / 100.0f - 0.5f;
                }
            }
            bias(0, f) = (rand() % 100) / 100.0f - 0.5f;
        }
    }

    Matrix forward(const Matrix& input) override {
        int outputRows = input.getRows() - kernelSize + 1;
        int outputCols = input.getCols() - kernelSize + 1;
        Matrix output(outputRows, numFilters);

        for (int f = 0; f < numFilters; ++f) {
            for (int i = 0; i < outputRows; ++i) {
                for (int j = 0; j < outputCols; ++j) {
                    float sum = 0.0f;
                    for (int ki = 0; ki < kernelSize; ++ki) {
                        for (int kj = 0; kj < kernelSize; ++kj) {
                            sum += input(i + ki, j + kj) * kernels[f](ki, kj);
                        }
                    }
                    output(i, f) = sum + bias(0, f);
                }
            }
        }
        return output;
    }
};

// MaxPooling слой
class MaxPooling2D : public Layer {
private:
    int poolSize;

public:
    MaxPooling2D(int ps) : poolSize(ps) {}

    Matrix forward(const Matrix& input) override {
        int outRows = input.getRows() / poolSize;
        int outCols = input.getCols();
        Matrix output(outRows, outCols);

        for (int c = 0; c < outCols; ++c) {
            for (int i = 0; i < outRows; ++i) {
                float maxVal = -INFINITY;
                for (int pi = 0; pi < poolSize; ++pi) {
                    maxVal = std::max(maxVal, input(i * poolSize + pi, c));
                }
                output(i, c) = maxVal;
            }
        }
        return output;
    }
};

// Транспонированная свертка (UpSampling)
class Conv2DTranspose : public Layer {
private:
    int kernelSize, stride;

public:
    Conv2DTranspose(int ks, int s) : kernelSize(ks), stride(s) {}

    Matrix forward(const Matrix& input) override {
        int outRows = input.getRows() * stride;
        int outCols = input.getCols();
        Matrix output(outRows, outCols);

        for (int c = 0; c < outCols; ++c) {
            for (int i = 0; i < input.getRows(); ++i) {
                for (int si = 0; si < stride; ++si) {
                    output(i * stride + si, c) = input(i, c);
                }
            }
        }
        return output;
    }
};

// U-Net архитектура
class UNet {
private:
    std::vector<Layer*> encoder;
    std::vector<Layer*> decoder;
    std::vector<Matrix> skipConnections;

public:
    UNet() {
        // Encoder path
        encoder.push_back(new Conv2D(3, 32));
        encoder.push_back(new Conv2D(3, 32));
        encoder.push_back(new MaxPooling2D(2));
        
        encoder.push_back(new Conv2D(3, 64));
        encoder.push_back(new Conv2D(3, 64));
        encoder.push_back(new MaxPooling2D(2));

        // Decoder path
        decoder.push_back(new Conv2DTranspose(2, 2));
        decoder.push_back(new Conv2D(3, 64));
        decoder.push_back(new Conv2D(3, 32));
        
        decoder.push_back(new Conv2DTranspose(2, 2));
        decoder.push_back(new Conv2D(3, 32));
        decoder.push_back(new Conv2D(3, 1));  // Выходной слой
    }

    ~UNet() {
        for (auto layer : encoder) delete layer;
        for (auto layer : decoder) delete layer;
    }

    Matrix forward(const Matrix& input) {
        skipConnections.clear();
        Matrix x = input;

        // Encoder
        for (size_t i = 0; i < encoder.size(); ++i) {
            x = encoder[i]->forward(x);
            if (i % 3 == 1) { // После двух сверток сохраняем для skip connection
                skipConnections.push_back(x);
            }
        }

        // Decoder с skip connections
        for (size_t i = 0; i < decoder.size(); ++i) {
            x = decoder[i]->forward(x);
            if (i == 2) { // После первого блока декодера добавляем skip connection
                if (!skipConnections.empty()) {
                    // Простое сложение (в реальности - конкатенация)
                    Matrix skip = skipConnections.back();
                    skipConnections.pop_back();
                    for (int r = 0; r < x.getRows(); ++r) {
                        for (int c = 0; c < x.getCols(); ++c) {
                            x(r, c) += skip(r, c % skip.getCols());
                        }
                    }
                }
            }
        }
        return x;
    }

    double dice_loss(const Matrix& pred, const Matrix& target) {
        double intersection = 0.0;
        double sum_pred = 0.0;
        double sum_target = 0.0;

        for (int i = 0; i < pred.getRows(); ++i) {
            for (int j = 0; j < pred.getCols(); ++j) {
                float p = std::min(1.0f, std::max(0.0f, pred(i, j)));
                float t = target(i, j);
                intersection += p * t;
                sum_pred += p;
                sum_target += t;
            }
        }

        double dice = (2.0 * intersection) / (sum_pred + sum_target + 1e-8);
        return 1.0 - dice;
    }
};

int main() {
    // Тестовый пример
    UNet unet;
    Matrix input(64, 1); // 64x1 упрощенный случай
    Matrix target(64, 1);

    // Заполнение тестовыми данными
    for (int i = 0; i < 64; ++i) {
        input(i, 0) = (rand() % 100) / 100.0f;
        target(i, 0) = (i % 8 == 0) ? 1.0f : 0.0f; // Простая маска
    }

    Matrix output = unet.forward(input);
    double loss = unet.dice_loss(output, target);

    std::cout << "Dice Loss: " << loss << std::endl;
    std::cout << "Output shape: " << output.getRows() << "x" << output.getCols() << std::endl;

    return 0;
}