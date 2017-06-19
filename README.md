# WDYApplication
![default](https://cloud.githubusercontent.com/assets/9795249/26715147/1b94607c-47a7-11e7-92dd-81494f8dbc7f.jpeg)
# 引用方法
<br>	allprojects {
<br>		repositories {
<br>		...
<br>			maven { url 'https://jitpack.io' }
<br>	}
<br>	}
<br> Step 2. Add the dependency
<br>	dependencies {
<br>	        compile 'com.github.wangdongyi:WDYApplication:1.0.4'
<br>	}

# 使用前一定要继承BaseApplication
public class BaseApplication extends com.base.library.application.BaseApplication 

# 工具一 TabLayoutUtils
修改TabLayout下面线的长度
用法 TabLayoutUtils.setIndicator(this, tab, 左边距（dp）,右边局（dp);
# 上传图片选取相册的api
PhotoUtils.showPhotoIntent(Context mContext, boolean isShowCamera, int MODE, int maxNum);
<br>参数：
<br>isShowCamera//是否显示相机 
<br>MODE;//单选多选0单选1多选 
<br>maxNum;//最多张数
返回 :
<br> @Override
<br> protected void onActivityResult(int requestCode, int resultCode, Intent data) {
<br> super.onActivityResult(requestCode, resultCode, data);
<br> PhotoUtils.onPhotoResult(resultCode,data, new PhotoUtils.onPhotoBack() {
<br> @Override
<br> public void onBack(ArrayList<String> result) {
<br> //图片路径 result
<br> }
<br> });
<br> }
# 图片浏览工具:
PhotoPreviewUtil.movePhotoPreview(this,list,0);
注释：
<br> Context context, ArrayList<String> urls, int selected,
<br> 跳转到图片预览 ArrayList<String> urls 图片地址 selected 选择位置
# 检验权限：
<br> checkPermission(ArrayList<String> permission, OnPermissionListen onPermissionListen)
<br> ArrayList<String> permission 需要申请的权限。
<br> OnPermissionListen 监听返回值
# 修改电池栏颜色：
<br>setStatusBar(boolean dark) true是黑色false白色。包含了小米，魅族和官方6.0以上系统。
