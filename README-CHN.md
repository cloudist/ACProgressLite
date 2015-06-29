# ACProgressLite

[![Build Status](https://api.travis-ci.org/Cloudist/ACProgressLite.svg?branch=master)](https://travis-ci.org/Cloudist/ACProgressLite)
<br/>[English Version](https://github.com/Cloudist/ACProgressLite/blob/master/README.md) / 中文版本  

Android 加载控件库，简洁、易用、可定制性强。用于快速实现类似 iOS 的 “加载中” 等弹出框。  
类似于 iOS 中的 [MBProgressHUD](https://github.com/jdg/MBProgressHUD)  

**关键词** *Progressbar, Progresswheel, HUD, Android, Loading*

**最小支持 SDK 版本:** API 9 ( Android 2.3 )

![花瓣 不带文字](https://raw.githubusercontent.com/Cloudist/ACProgressLite/master/intros/screenshot_0.png)
![花瓣 带文字](https://raw.githubusercontent.com/Cloudist/ACProgressLite/master/intros/screenshot_1.png)
![圆饼](https://raw.githubusercontent.com/Cloudist/ACProgressLite/master/intros/screenshot_2.png)
![自定义](https://raw.githubusercontent.com/Cloudist/ACProgressLite/master/intros/screenshot_3.png)


[示例 APK 下载](https://raw.githubusercontent.com/Cloudist/ACProgressLite/master/intros/sample.apk)  

## 当前版本
* 1.2.1

## 使用方式
* gradle: `compile 'cc.cloudist.acplibrary:library:1.2.1`

## 示例代码
* 花瓣类型

```
ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .text("Title is here)
                        .fadeColor(Color.DKGRAY).build();
dialog.show();
```

* 圆饼类型

```
ACProgressPie dialog = new ACProgressPie.Builder(this)
                     .ringColor(Color.WHITE)
                     .pieColor(Color.WHITE)
                     .updateType(ACProgressConstant.PIE_AUTO_UPDATE)
                     .build();
dialog.show();
```

* 自定义类型

```
ACProgressCustom dialog = new ACProgressCustom.Builder(this)
                        .useImages(R.drawable.p0, R.drawable.p1, R.drawable.p2, R.drawable.p3)
                        .build();
dialog.show();
```

## 配置说明
&emsp;目前提供3种类型的加载框：<br/>  
&emsp;下面是一些通用的配置：
1.  `sizeRatio` 背景的大小。值应为小于1的浮点数，表示以屏幕较短一边的相应比例为框体大小，即：  
背景边长 = 屏幕较短一边的长度 * sizeRatio  
需要注意的是花瓣类型带文字的情况，下文有详细说明

1.  `bgColor` 背景的颜色，整型值。
1.  `bgAlpha` 背景的透明度，0为全透明，1为不透明，其他透明度设置也类似。
1.  `bgCornerRadius` 背景四个圆角的半径。

*其中 bgColor、bgAlpha、bgCornerRadius 不适用于自定义类型*


* **花瓣类型**  
最常见的类型，支持标题文字显示

设置|说明
:------|:------------------
themeColor|花瓣起始颜色
borderPadding|花瓣外沿与背景边缘的距离占背景边长的比例（背景边长指的是根据sizeRatio计算出来的长度）
centerPadding|花瓣内沿与背景中心的距离占背景边长的比例（背景边长指的是根据sizeRatio计算出来的长度）
fadeColor|花瓣终止颜色
petalCount|花瓣数量
petalAlpha|花瓣的透明度
petalThickness|花瓣的粗细
direction|花瓣的旋转方向，顺时针`ACProgressConstant.DIRECT_CLOCKWISE`或逆时针`DIRECT_ANTI_CLOCKWISE`
speed|旋转速度，每秒的帧数
text|文本标题，显示在花瓣下方
textSize|文字大小
textColor|文字颜色
textAlpha|文字透明度
textMarginTop|文字与花瓣之间的距离
isTextExpandWidth|在设置了文字的情况下是否扩展背景大小值正方形。设置了文字会导致背景高度变大，如果设为`true`花瓣仍然根据sizeRatio的大小绘制但整个宽度会扩展至与高度相等，花瓣水平居中；如果设为`false`背景为长方形

* **圆饼类型**  
适合显示进度，支持自动更新进度或者手动更新进度。

设置|说明
:------|:------------------
ringColor|圆环的颜色
ringAlpha|圆环的透明度
ringThickness|圆环的粗细
ringBorderPadding|圆环与背景外沿与背景边缘的距离占背景边长的比例
pieColor|圆饼的颜色
pieAlpha|圆饼的透明度
pieRingDistance|圆饼与圆环的距离占背景边长的比例
updateType|更新模式。自动更新`PIE_AUTO_UPDATE`或者手动更新`PIE_MANUAL_UPDATE`。手动更新需要调用`setPiePercentage()`。
speed|自动更新模式下每秒的帧数
pieces|自动更新模式下圆饼被切分的块数


* **自定义类型**  
类似于 GIF，支持 res/drawable 资源数组 或者 图片文件数组 作为数据源。

设置|说明
:------|:------------------
useImages|使用的图片资源 ID
useFiles|使用的图片文件对象
speed|每秒的帧数

## 注意
* 遇到任何问题都可以优先尝试 **clean** 工程
* 1.2.0+ 版本如果需要点击弹框之外的区域关闭请使用`setCanceledOnTouchOutside(true)`
* `Dialog` 的绝大部分方法都支持
* 强烈建议使用 1.2.0+ 版本
* 如果使用 1.1.0 以前的版本 (不含1.1.0), 如果存在 gradle 错误, 可以在 Manifest 的 `application` 下增加 `tools:ignore="label`"解决
* 自定义类型中不要同时使用 `useImages` 与 `useFiles`, 否则只有最后一次设置是有效的.
* 默认的配置在多数情况下表现良好. 如果需要自定义样式, 请调节各个配置以达到最佳的显示效果.

## 许可
* [MIT License](http://mit-license.org/)