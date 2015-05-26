# ACProgressLite
---
A concise Android progressBar library - "Android Cloudist Progress Lite"

easy to use and config

support API_INT >= 9

Suggestions and pull requests are welcomed. :-)

## Version
---
* 1.0.0

## Installation
---
* gradle: `complie"xxx.xxx"`

* import moudle:"library" to your project

## Sample And Usage
---

ACProgressLite needs to use **Builder** to create instances.

* "Flower"

`new ACProgressFlower.Builder(context).build().show();`

* "Pie"

`new ACProgressPie.Builder(context).build().show();`
* Custom


    ACProgressCustom.Builder builder = new ACProgressCustom.Builder(context);
    builder.useImages(?,?,?);
    builder.build().show();


### **Some frequently-used configurations：**
<br/>
#### *General*
* **sizeRatio**: The Size Ratio of the background, length of the smaller edge of screen is 1. This is a float value which small than 1. (Looks like a percentage value, like 0.5f)
* **speed**: The number of the frames per second.
* **setCancelable(boolean)**: Whether this progress can be dismissed by click the outside area.

#### *"Flower"*
* **direction**: Direction of rotation *DIRECT_CLOCKWISE or DIRECT_ANTI_CLOCKWISE*
* **themeColor**: Start color
* **fadeColor**: End color
* **petalCount**: Petals count
* **borderPadding**：Distance between out-edge of petals and edge of background / background's side length
* **centerPadding**：Distance between inner-edge of petals and center of background / background's side length

#### *"Pie"*
* **ringColor**: Ring's color
* **pieColor**: Pie's color
* **ringBorderPadding**: Distance between ring and edge of background / background's side length
* **pieRingDistance**: Distance between out-edge of pie and ring / background's side length

#### *Custom*
* **useImages**: Use drawable resources as the image source, parameters are resource ids.
* **useFiles**: Use local image files as the image source, parameters are files' paths.

### **Notice**
<br/>
* Do not use `useImages` and `useFiles' at the same time, or only the last configuration is avaliable.
* All configurations works fine by default. If you want to change the default, please tweak different configuration carefully to make the progress looks perfectly.
* Do not call `dismiss()` when the progress is not on the screen, otherwise you may get a `RemoveACPLException`.


## License
---
* [MIT License](http://mit-license.org/)