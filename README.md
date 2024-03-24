<h1 align="center" style="border-bottom: none;">SnapSnatch</h1>

<p align="center">
    <a href="https://github.com/kyleryxn/snapsnatch/actions/workflows/maven.yml">
        <img src="https://github.com/kyleryxn/snapsnatch/actions/workflows/maven.yml/badge.svg" alt="Maven build workflow">
    </a>
    <a href="https://github.com/kyleryxn/snapsnatch/actions/workflows/release.yml">
        <img src="https://github.com/kyleryxn/snapsnatch/actions/workflows/release.yml/badge.svg" alt="Release workflow">
    </a>
</p>
<p align="center">
    <a href="LICENSE">
        <img src="https://img.shields.io/badge/License-MIT-yellow" alt="License">
    </a>
    <a href="https://maven.apache.org/">
        <img src="https://img.shields.io/badge/Built With-Maven-C71A36?&logo=apache-maven" alt="Apache Maven">
    </a>
</p>

SnapSnatch emerged from the creative depths of a job interview hackathon, where an ingenious idea sparked the birth of
a web crawling marvel. Originally conceived as a humble project, its evolution from the primitive prototype to its
current form was marked by a journey of innovation and refinement. Before SnapSnatch, there was PictoCrawl, a precursor
that laid the groundwork for its successor's greatness.

## Overview

SnapSnatch is your digital companion on a thrilling adventure through the vast expanse of the internet, handpicking
images from websites along the way. Imagine it as a friendly explorer, equipped with the latest technology to venture
into the depths of cyberspace in search of visual treasures.

At the heart of SnapSnatch's magic lies its ability to multitask effortlessly. Like a nimble acrobat, it gracefully
juggles many tasks simultaneously, ensuring swift and seamless image retrieval. This seamless operation is made
possible by cleverly managing its interactions with websites, maintaining a harmonious balance between efficiency and
respect for online resources.

But that's not all! SnapSnatch possesses a sophisticated communication network that rivals the camaraderie of a
well-coordinated team. Think of it as a symphony conductor, orchestrating a harmonious exchange of information between
its various components. This enables SnapSnatch to adapt and respond dynamically to any challenge it encounters on its
quest for images.

So, whether you're embarking on a creative project, conducting research, or simply indulging in a visual feast, let
SnapSnatch be your trusted companion. With its blend of innovation, efficiency, and charm, it's more than just a
tool—it's a companion on your journey through the digital landscape.

## Features

- **Multithreading:** Picture SnapSnatch as a skilled multitasker, effortlessly juggling multiple tasks at once. With
  the power of virtual threads, it can dance through the digital realm, swiftly grabbing images from different corners
  of the internet without missing a beat.
- **Connection Pooling:** SnapSnatch is not just about speed; it's also smart about how it interacts with websites. By
  pooling connections like a seasoned diplomat, it maintains harmonious relations with the online world, ensuring smooth
  and efficient image retrieval without overwhelming its digital hosts. Thanks to the clever tricks learned from [Apache
  HttpComponents][apache-hc], SnapSnatch navigates the internet with finesse and grace.

## Changes from PictoCrawl

1. Transition from Java 17 to Java 21 for leveraging the latest language features and optimizations.
2. Shift from using platform threads to virtual threads, enhancing concurrency and scalability in the application.
3. Migration from Spring Web MVC with Jakarta EE to Spring Boot, simplifying configuration and enhancing development 
   efficiency.
4. Refactor packaging strategy from the conventional package by layer approach to package by feature, promoting better
   organization and modularity.
5. Enable microservices architecture to enhance scalability, maintainability, and deployment flexibility of the
   application.

## Project Structure

In SnapSnatch, packages contain all classes that are required for a feature. The independence of the package is ensured
by placing closely related classes in the same package. The use of a class by a class in another package is eliminated
with this structure. Also, the classes within the packages are closely related to each other. Thus, there is **high
cohesion within packages** and **low coupling between packages**. In addition, this structure provides higher
modularity.

## Installation

### Prerequisites

- Java 21
- Maven 3.9

### Getting Started

For now, you can clone this repository onto your machine:

```
$ git clone git@github.com:kyleryxn/snapsnatch.git
```

## License

This project is licensed under the MIT License. See the [LICENSE][license] file for details.

## Acknowledgements

We would like to thank the developers of the libraries and tools used in this project for their valuable contributions
to the Java ecosystem.

Thank you to M. Enes Oral for this wonderful [article][package-feature] outlining the differences between package by
layer and package by feature.

<!-- Links -->
[license]: ./LICENSE
[apache-hc]: https://hc.apache.org/index.html
[package-feature]: https://medium.com/sahibinden-technology/package-by-layer-vs-package-by-feature-7e89cde2ae3a
[maven-ci-passing]: https://github.com/kyleryxn/snapsnatch/actions/workflows/maven.yml
[release-ci-passing]: https://github.com/kyleryxn/snapsnatch/actions/workflows/release.yml

<!-- Badges -->
[license-badge]: https://img.shields.io/badge/License-MIT-yellow
[maven-badge]: https://img.shields.io/badge/License-MIT-C71A36&logo=apache-maven
[maven-ci-badge]: https://github.com/kyleryxn/snapsnatch/actions/workflows/maven.yml/badge.svg
[release-ci-badge]: https://github.com/kyleryxn/snapsnatch/actions/workflows/release.yml/badge.svg

<!-- Images -->
[maven]: ./docs/images/maven-feather.png
