[![Build Status](https://travis-ci.org/wookieJ/image-editor.svg?branch=master)](https://travis-ci.org/wookieJ/image-editor)
<!--[![codecov](https://codecov.io/gh/wookieJ/image-editor/branch/master/graph/badge.svg)](https://codecov.io/gh/wookieJ/image-editor)-->
# image-processing

Image processing application, based on Plug-In architecture. The basic functionality includes drawing simple figures with the indicated color and line size. There is implemented possibility of undoing changes and restoring them. It is possible to load an image from a file.

## Plugins
There are four example plugins implemented which are dynamically loading by reflection at the beginning of program.
* Grey scale - turning color image into grey scale.
* Blur - bluring image.
* Equalize - filter which makes image look better.
* Face detection - detects faces on image.

## Screenshots
<p align="center">
  <img width="750" height="570" src="../master/sampleAssets/screen.PNG">
</p>

<p align="center">
  <img width="750" height="411" src="../master/sampleAssets/screen2.PNG">
</p>

## Getting Started
You need maven nd JDK 8.<br/>
To clone repository use following command:

```
https://github.com/wookieJ/image-editor.git
```

## Installing

To build and install project use following command:
```
mvn clean install compile
```
## Adding own PlugIns
To add your own plugin, you need to create a project with image-processing.jar as its library. You can find it in target directory. Main class should extends Plugin abstract class and override it's methods. You should also add config.cfg file to resource. Example config.cfg file below:

```
Name "Face Detection"
Main pl.put.FaceDetectionFilterPlugin
```

**Main** property indicates main class path

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management
* [Travis](https://travis-ci.org/) - Test and Deploy tool

## Tech Stack
* JavaFX
* Groovy
* Spock
* OpenCV

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
