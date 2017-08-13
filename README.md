TODO: add header

## About
This project is maintained by Ramotion, Inc.<br>
We specialize in the designing and coding of custom UI for Mobile Apps and Websites.<br>

**Looking for developers for your project?**<br>
This project is maintained by Ramotion, Inc. We specialize in the designing and coding of custom UI for Mobile Apps and Websites.

TODO: add images and animation

## Requirements
​
- Android 4.4 KitKat (API lvl 19) or greater
- Your favorite IDE

## Basic usage

`GarlandView` consists of classes for inner items, those that are scrolled vertically.
And outer items, those that are scrolled horizontally and each of which contains,
one inner item.

First of all, you need to implement the classes necessary to create internal items.
These are the classes: InnerItem and InnerAdapter.

`InnerAdapter` is an abstract class inherited from RecyclerView.Adapter,
It works only with InnerItem - ViewHolder. An example implementation, see the example.

In `InnerItem`, you need to override the `getInnerLayout` method, which must return
main layout of the inner item. An example implementation, see the example.

Next, you need to override the classes required for external items.
These are the classes: `HeaderItem` and` HeaderAdapter`

`HeaderAdapter` is an abstract class inherited from RecyclerView.Adapter,
It works only with HeaderItem - ViewHolder. An example implementation, see the example.

In `HeaderItem`, you need to redefine 4 methods:` getHeader`, `getHeaderAlphaView`,` isScrolling`, `getViewGroup`.
The method `getViewGroup` should return InnerRecyclerView.
The `isScrolling` method must return the InnerRecyclerView's scrolling state.
The `getHeaderAlpha` method should return alpha-layout, which will be used for dimming (hiding header's views).
The `getHeader` method must return the main layout of the header, an outer item.
An example implementation, see the example.

Finally, in the Activity's layout, place `TailRecyclerView`. Next, create a TailLayoutManager and
specify it as a LayoutManager, for `TailRecyclerView`. An example implementation, see the example.

Here are the attributes of `TailRecyclerView` you can specify in the XML layout:
*`itemStart` - Outer item left and right offset size.
*`itemGap` -  Distance between outer items.

## Installation
TODO: write Installation

## License
TODO: add License