<div align="center">

![UAW-Banner-Ex2.png](https://i.postimg.cc/nc9655q7/HAI-Banner-Ex2.png)
[![MadeWithJava](https://img.shields.io/badge/Made%20with-java-red?style=for-the-badge&logo=java)](https://en.wikipedia.org/wiki/Java_%28programming_language%29)
[![License](https://img.shields.io/github/license/Eschatologue/Unlimited-Armament-Works?color=important&logo=Github&style=for-the-badge)](https://mit-license.org/)
[![Build](https://img.shields.io/github/workflow/status/Eschatologue/Unlimited-Armament-Works/Java%20CI?logo=Gradle&style=for-the-badge)](https://github.com/Eschatologue/Unlimited-Armament-Works/actions)
[![Discord](https://img.shields.io/discord/704355237246402721.svg?color=7289da&label=de_server&logo=discord&logoColor=ffffff&style=for-the-badge)](https://discord.gg/RCCVQFW)

</div>

<h1 align="center"> 
UNLIMITED ARMAMENT WORKS
</h1> 

<div align="center">

My first java mod, a continuation of [Heavy Armaments Industries](https://github.com/Eschatologue/Heavy-Armaments-Industries), all future content will be implemented here instead of the old repository.

**Unlimited Armament Works**, which the name obviously not inspired by a certain anime title, is both a port and a sequel to **Heavy Armament Industries**. 

Featuring contents that expands upon vanilla Mindustry, adding more turrets, units, factories, and much more that meant to be played side by side with vanilla Mindustry.

</div> 

<h1 align="center"> 
DOWNLOAD
</h1> 

<div align="center">

[![File Size](https://img.shields.io/github/repo-size/Eschatologue/Unlimited-Armament-Works?color=62ae7f&label&style=for-the-badge)](https://github.com/Eschatologue/Unlimited-Armament-Works/releases)
[![Download](https://img.shields.io/github/v/release/Eschatologue/Unlimited-Armament-Works?color=62ae7f&include_prereleases&label=Latest%20version&logo=github&logoColor=white&style=for-the-badge)](https://github.com/Eschatologue/Unlimited-Armament-Works/releases)
[![Total Downloads](https://img.shields.io/github/downloads/Eschatologue/Unlimited-Armament-Works/total?color=62ae7f&label&logo=docusign&logoColor=white&style=for-the-badge)](https://github.com/Eschatologue/Unlimited-Armament-Works/releases)

Also available in the in-game mod browser, where installation is automatic, just search for **Unlimited-Armament-Works** and you're done.

For unreleased builds you can find it at the [Actions](https://github.com/Eschatologue/Unlimited-Armament-Works/actions) tab and open the last workflow with a green ✅. The zip will contain the latest unreleased build of **UAW** which you should install with caution since it may crashes your game.

Only compatible on Desktop and Android, both Java and JavaScript mod doesn't work on iOS.

</div>

## Software Used
- Intellij IDEA
- GitHub Desktop
- Aseprite [Making Sprites]
- Inkscape [Making Banner and Logo]
- Audacity [Editing audio used in the mod]
---
## Building for Desktop Testing

1. Install JDK **16**.
2. Run `gradlew jar` [1].
3. Your mod jar will be in the `build/libs` directory. **Only use this version for testing on desktop. It will not work with Android.**
To build an Android-compatible version, you need the Android SDK. You can either let Github Actions handle this, or set it up yourself. See steps below.

## Building through Github Actions

This repository is set up with Github Actions CI to automatically build the mod for you every commit. This requires a Github repository, for obvious reasons.
To get a jar file that works for every platform, do the following:
1. Make a Github repository with your mod name, and upload the contents of this repo to it. Perform any modifications necessary, then commit and push. 
2. Check the "Actions" tab on your repository page. Select the most recent commit in the list. If it completed successfully, there should be a download link under the "Artifacts" section. 
3. Click the download link (should be the name of your repo). This will download a **zipped jar** - **not** the jar file itself [2]! Unzip this file and import the jar contained within in Mindustry. This version should work both on Android and Desktop.

## Building Locally

Building locally takes more time to set up, but shouldn't be a problem if you've done Android development before.
1. Download the Android SDK, unzip it and set the `ANDROID_HOME` environment variable to its location.
2. Make sure you have API level 30 installed, as well as any recent version of build tools (e.g. 30.0.1)
3. Add a build-tools folder to your PATH. For example, if you have `30.0.1` installed, that would be `$ANDROID_HOME/build-tools/30.0.1`.
4. Run `gradlew deploy`. If you did everything correctlly, this will create a jar file in the `build/libs` directory that can be run on both Android and desktop. 

## Adding Dependencies

Please note that all dependencies on Mindustry, Arc or its submodules **must be declared as compileOnly in Gradle**. Never use `implementation` for core Mindustry or Arc dependencies. 

- `implementation` **places the entire dependency in the jar**, which is, in most mod dependencies, very undesirable. You do not want the entirety of the Mindustry API included with your mod.
- `compileOnly` means that the dependency is only around at compile time, and not included in the jar.

Only use `implementation` if you want to package another Java library *with your mod*, and that library is not present in Mindustry already.

--- 

*[1]* *On Linux/Mac it's `./gradlew`, but if you're using Linux I assume you know how to run executables properly anyway.*  
*[2]: Yes, I know this is stupid. It's a Github UI limitation - while the jar itself is uploaded unzipped, there is currently no way to download it as a single file.*

---

## Notes
If you have any questions, ideas, or feedback, please DM `Geschiedenis#4783` at Discord, I will try my best to reply.

<div align="center">

[![forthebadge](https://forthebadge.com/images/badges/built-with-love.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/it-works-why.svg)](https://forthebadge.com)

</div>