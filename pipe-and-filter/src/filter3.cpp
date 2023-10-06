#include <opencv2/imgproc/imgproc.hpp>

// FILTER 3: convert image to grey scale
void filter3(cv::Mat img)
{
    int width = img.cols;
    int height = img.rows;
    for (int i = 0; i < height; i++)
    {
        for (int j = 0; j < width; j++)
        {
            cv::Vec3b pixel = img.at<cv::Vec3b>(i, j);
            uchar R = pixel[2];
            uchar G = pixel[1];
            uchar B = pixel[0];
            uchar avg = (R + G + B) / 3;
            pixel[0] = pixel[1] = pixel[2] = avg;
            img.at<cv::Vec3b>(i, j) = pixel;
        }
    }
}