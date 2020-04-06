<a href="https://www.ramotion.com/agency/app-development/?utm_source=gthb&utm_medium=repo&utm_campaign=garland-view-android"><img src="https://github.com/Ramotion/folding-cell/blob/master/header.png"></a>

<a href="https://github.com/Ramotion/garland-view-android">
<img align="left" src="https://github.com/Ramotion/garland-view-android/blob/master/preview.gif" width="480" height="360" /></a>

<p><h1 align="left">GARLAND VIEW [JAVA]</h1></p>

<h4>Garland View seamlessly transitions between multiple lists of content</h4>


___


<p><h6>We specialize in the designing and coding of custom UI for Mobile Apps and Websites.</h6>
<a href="https://www.ramotion.com/agency/app-development/?utm_source=gthb&utm_medium=repo&utm_campaign=garland-view-android">
<img src="https://github.com/ramotion/gliding-collection/raw/master/contact_our_team@2x.png" width="187" height="34"></a>
</p>
<p><h6>Stay tuned for the latest updates:</h6>
<a href="https://goo.gl/rPFpid" >
<img src="https://i.imgur.com/ziSqeSo.png/" width="156" height="28"></a></p>

</br>

[![Twitter](https://img.shields.io/badge/Twitter-@Ramotion-blue.svg?style=flat)](http://twitter.com/Ramotion)
[![Donate](https://img.shields.io/badge/Donate-PayPal-blue.svg)](https://paypal.me/Ramotion)

# Check this library on other platforms:
<a href="https://github.com/Ramotion/garland-view">
<img src="https://github.com/ramotion/navigation-stack/raw/master/Swift@2x.png" width="178" height="81"></a>

## Requirements
- Android 4.4 KitKat (API lvl 19) or greater
- Your favorite IDE

## Installation
Just download the package from [here](http://central.maven.org/maven2/com/ramotion/garlandview/garland-view/0.3.3/garland-view-0.3.3.aar) and add it to your project classpath, or just use the maven repo:

Gradle:
```groovy
compile 'com.ramotion.garlandview:garland-view:0.3.3'
```
SBT:
```scala
libraryDependencies += "com.ramotion.garlandview" % "garland-view" % "0.3.3"
```
Maven:
```xml
<dependency>
    <groupId>com.ramotion.garlandview</groupId>
    <artifactId>garland-view</artifactId>
    <version>0.3.3</version>
</dependency>
```


## Basic usage

`GarlandView` consists of classes for inner items that are scrolled vertically
and outer items that are scrolled horizontally, and each of which contains
one inner item.

First of all, you need to implement the classes necessary to create internal items: InnerItem and InnerAdapter.

`InnerAdapter` is an abstract class inherited from RecyclerView.Adapter.
It works only with InnerItem - ViewHolder.

In `InnerItem`, you need to override the `getInnerLayout` method, which must return
the main layout of the inner item.

Next, you need to override the classes required for external items: `HeaderItem` and` HeaderAdapter`.

`HeaderAdapter` is an abstract class inherited from RecyclerView.Adapter,
It works only with HeaderItem - ViewHolder.

In `HeaderItem`, you need to redefine 4 methods:` getHeader`, `getHeaderAlphaView`,` isScrolling`, `getViewGroup`.
The method `getViewGroup` should return InnerRecyclerView.
The `isScrolling` method must return the InnerRecyclerView's scrolling state.
The `getHeaderAlpha` method should return an alpha-layout, which will be used for dimming (hiding header's views).
The `getHeader` method must return the main layout of the header, an outer item.

Finally, place `TailRecyclerView` in the Activity's layout. Next, create a TailLayoutManager and
specify it as a LayoutManager for `TailRecyclerView`.

Here are the attributes of `TailRecyclerView` you can specify in the XML layout:
* `itemStart` - Outer item left and right offset size.
* `itemGap` -  Distance between outer items.

<br>

## 🗂 Check this library on other language:
<a href="https://github.com/Ramotion/garland-view">
<img src="https://github.com/ramotion/navigation-stack/raw/master/Swift@2x.png" width="178" height="81"></a>

## 📄 License

Garland View Android is released under the MIT license.
See [LICENSE](./LICENSE) for details.

This library is a part of a <a href="https://github.com/Ramotion/android-ui-animation-components-and-libraries"><b>selection of our best UI open-source projects</b></a>

If you use the open-source library in your project, please make sure to credit and backlink to www.ramotion.com

## 📱 Get the Showroom App for Android to give it a try
Try this UI component and more like this in our Android app. Contact us if interested.

<a href="https://play.google.com/store/apps/details?id=com.ramotion.showroom" >
<img src="https://raw.githubusercontent.com/Ramotion/react-native-circle-menu/master/google_play@2x.png" width="104" height="34"></a>

<a href="https://www.ramotion.com/agency/app-development/?utm_source=gthb&utm_medium=repo&utm_campaign=garland-view-android">
<img src="https://github.com/ramotion/gliding-collection/raw/master/contact_our_team@2x.png" width="187" height="34"></a>
