# ACProgressLite

一个简洁易用、类似 iOS 的 Android progress wheel 与 indicator library
"Android Cloudist Progress Lite"  


使用简单配置丰富

支持API版本： API_INT >= 9

[演示APK](https://github.com/Cloudist/ACProgressLite/raw/master/sample.apk)

欢迎讨论 :-)

[English Version](https://github.com/Cloudist/ACProgressLite/blob/master/README.md)

## 版本

* 1.1.5

## 安装

* gradle: `compile 'cc.cloudist.acplibrary:library:1.1.5'`

* 直接将 module: "library" 引入工程

## 示例与用法
---

![Flower](https://raw.githubusercontent.com/Cloudist/ACProgressLite/master/acpl1.gif)
![Pie](https://raw.githubusercontent.com/Cloudist/ACProgressLite/master/acpl2.gif)
![Custom](https://raw.githubusercontent.com/Cloudist/ACProgressLite/master/acpl3.gif)

使用 ACProgressLite 需要通过对应的 **Builder**.

* "菊花"类型

`new ACProgressFlower.Builder(context).build().show();`

* "圆饼"类型

`new ACProgressPie.Builder(context).build().show();`

* 自定义类型

```
ACProgressCustom.Builder builder = new ACProgressCustom.Builder(context);
builder.useImages(?,?,?);
builder.build().show();
```

### **常用配置：**
#### *通用*
* **sizeRatio**: 背景占屏幕的比例, 这个值是float类型, 取屏幕宽高中较短的一边为1
* **speed**: 每秒播放的帧数
* **setCancelable(boolean)**: 是否可以通过点击 ACProgressLite 以外的区域将其 dismiss

#### *"菊花"类型*
* **direction**: 旋转方向 *DIRECT_CLOCKWISE(顺时针) or DIRECT_ANTI_CLOCKWISE(逆时针)*
* **themeColor**: 起始颜色
* **fadeColor**: 终止颜色
* **petalCount**: 花瓣数量
* **borderPadding**：花瓣外侧与背景边缘的距离 / 背景的边长
* **centerPadding**：花瓣内侧与背景中心点的距离 / 背景的边长

#### *"圆饼"类型*
* **ringColor**: 圆环的颜色
* **pieColor**: 圆饼的颜色
* **ringBorderPadding**: 圆环与背景边缘的距离 / 背景的边长
* **pieRingDistance**: 圆环与圆饼的距离 / 背景的边长

#### *自定义类型*
* **useImages**: 使用 drawable 资源作为图片源, 参数是资源id.
* **useFiles**: 使用本地文件作为图片源, 参数是文件路径.

### **注意**
* 在提issue之前, 可以尝试 **Clean** 下工程.
* 如果使用1.1.0以前的版本 (不含1.1.0), 如果存在 gradle 错误, 可以在 Manifest 的 `application` 下增加 `tools:ignore="label`"解决
* 不要同时使用 `useImages` 与 `useFiles`, 否则只有最后一次设置是有效的.
* 默认的配置在多数情况下表现良好. 如果需要自定义样式, 请调节各个配置以达到最佳的显示效果.
* 避免在屏幕上没有显示 ACProgressLite 时调用 `dismiss()`.
* **不要**在调用完一个对象的`dismiss()`后再次调用其`show()`. 每次调用`show()`之前都应初始化新对象, 否则会出现空异常. *在未来版本中会对这一点进行修改, 以支持`dismiss()`后调用`show()`*


## 许可

* [MIT License](http://mit-license.org/)