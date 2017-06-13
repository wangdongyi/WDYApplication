# WDYApplication
![default](https://cloud.githubusercontent.com/assets/9795249/26715147/1b94607c-47a7-11e7-92dd-81494f8dbc7f.jpeg)
# 使用前一定要继承BaseApplication
public class BaseApplication extends com.base.library.application.BaseApplication 

# 工具一 TabLayoutUtils
修改TabLayout下面线的长度
用法 TabLayoutUtils.setIndicator(this, tab, 左边距（dp）,右边局（dp);
# 上传图片选取相册的api
PhotoUtils.showPhotoIntent(Context mContext, boolean isShowCamera, int MODE, int maxNum);
参数：isShowCamera//是否显示相机 
     MODE;//单选多选0单选1多选 
     maxNum;//最多张数
返回 :
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoUtils.onPhotoResult(resultCode,data, new PhotoUtils.onPhotoBack() {
            @Override
            public void onBack(ArrayList<String> result) {
                //图片路径 result
           }
        });
    }
# 图片浏览工具:
PhotoPreviewUtil.movePhotoPreview(this,list,0);
  注释：Context context, ArrayList<String> urls, int selected,
  跳转到图片预览 ArrayList<String> urls 图片地址 selected 选择位置
