// Include the GUI and image processing header files from OpenCV
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <chrono>
#include <ctime>
#include <cstdlib>
#include <iostream>
#include <filesystem>
#include "image_compare.cpp"

int main(int32_t argc, char **argv)
{

    namespace fs = std::filesystem;
    std::string folderPath = "../images/";

    // loop for each image
    for (const auto &entry : fs::directory_iterator(folderPath))
    {
        if (fs::is_regular_file(entry) && entry.path().extension().string() == ".jpg")
        {
            // extract image and load to image object
            std::string path = entry.path().filename().string();
            cv::Mat img = cv::imread(folderPath + "/" + path, cv::IMREAD_COLOR);

            int width = img.cols;
            int height = img.rows;

            // FILTER 1: flip image along horizontal axis
            for (int i = 0; i < height; i++)
            {
                for (int j = 0; j < width / 2; j++)
                {
                    cv::Vec3b pixel = img.at<cv::Vec3b>(i, j);
                    img.at<cv::Vec3b>(i, j) = img.at<cv::Vec3b>(i, width - 1 - j);
                    img.at<cv::Vec3b>(i, width - 1 - j) = pixel;
                }
            }

            // FILTER 2: flip image along vertical axis
            for (int i = 0; i < height / 2; i++)
            {
                for (int j = 0; j < width; j++)
                {
                    cv::Vec3b pixel = img.at<cv::Vec3b>(i, j);
                    img.at<cv::Vec3b>(i, j) = img.at<cv::Vec3b>(height - 1 - i, j);
                    img.at<cv::Vec3b>(height - 1 - i, j) = pixel;
                }
            }

            // FILTER 3: convert image to grey scale
            for (int i = 0; i < height; i++)
            {
                for (int j = 0; j < width; j++)
                {
                    cv::Vec3b pixel = img.at<cv::Vec3b>(i, j);
                    uchar r = pixel[2];
                    uchar g = pixel[1];
                    uchar b = pixel[0];
                    uchar avg = (r + g + b) / 3;
                    pixel[0] = pixel[1] = pixel[2] = avg;
                    img.at<cv::Vec3b>(i, j) = pixel;
                }
            }

            // Save the resulting image
            cv::imwrite("../" + path, img);

            // CI code, not relevant for the student
            cv::Mat result = cv::imread("../solution/result_" + path, cv::IMREAD_GRAYSCALE);
            bool equalImages = areMatObjectsEqual(img, result);
            if (!equalImages)
            {
                return 1;
            };
        }
    }
    return 0;
}
