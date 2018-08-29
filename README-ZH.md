# BallsLoadingView
一个包含四个做循环动画的小球的loading view

### [更多](https://github.com/samlss/FunnyViews)

 <br/>

[![Api reqeust](https://img.shields.io/badge/api-11+-green.svg)](https://github.com/samlss/BallsLoadingView)  [![MIT Licence](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://github.com/samlss/BallsLoadingView/blob/master/LICENSE) [![Blog](https://img.shields.io/badge/samlss-blog-orange.svg)](https://blog.csdn.net/Samlss)


![gif1](https://github.com/samlss/BallsLoadingView/blob/master/screenshots/screenshot1.gif)

![gif2](https://github.com/samlss/BallsLoadingView/blob/master/screenshots/screenshot2.gif)

![gif3](https://github.com/samlss/BallsLoadingView/blob/master/screenshots/screenshot3.gif)

### 使用<br>
在根目录的build.gradle添加这一句代码：
```java
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

在app目录下的build.gradle添加依赖使用：
```java
dependencies {
    implementation 'com.github.samlss:BallsLoadingView:1.0'
}
```


布局中：
```java
 <com.iigo.library.BallsLoadingView
          android:layout_marginTop="50dp"
          app:pointRadius="6dp"
          app:animType="scale"
          app:firstPointColor="@android:color/holo_green_dark"
          app:secondPointColor="@android:color/holo_red_dark"
          app:thirdPointColor="@android:color/holo_orange_dark"
          app:fourthPointColor="@android:color/holo_blue_dark"
          android:layout_width="100dp"
          android:layout_height="40dp" />

```

<br>

代码：
```java
  ballsLoadingView.setAnimType(BallsLoadingView.ANIM_TYPE_SCALE); //设置动画类型
  
  ballsLoadingView.setBallRadius(6); //设置球的半径大小(像素)
  
  ballsLoadingView.setFirstBallColor(Color.RED); //设置第一个球的颜色
  ballsLoadingView.setSecondBallColor(Color.BLACK); //设置第二个球的颜色
  ballsLoadingView.setThirdBallColor(Color.GREEN); //设置第三个球的颜色
  ballsLoadingView.setFourthBallColor(Color.BLUE); //设置第四个球的颜色
  
  ballsLoadingView.start(); //开始动画
  ballsLoadingView.stop(); //停止动画
  
  ballsLoadingView.release(); //不需要使用该loading view的时候可手动释放，例如在activity的ondestroy()中
```
<br>

属性说明：

| 属性      |              说明              |
| --------- | :-----------------------------------: |
| firstBallColor | 第一个球的颜色 |
| secondBallColor | 第二个球的颜色|
| thirdBallColor | 第三个球的颜色 |
| fourthBallColor | 第四个球的颜色 |
| ballRadius | 球的半径 |
| animType | 动画类型(translate, scale) |

<br>

# 注意

我没有开放设置动画插值器和动画时间的相关接口，如果你需要拓展更多的功能，你可以通过下载源码进行修改

## [LICENSE](https://github.com/samlss/BallsLoadingView/blob/master/LICENSE)