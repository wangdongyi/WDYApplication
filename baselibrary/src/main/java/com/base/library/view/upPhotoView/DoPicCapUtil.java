package com.base.library.view.upPhotoView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.base.library.util.CodeUtil;
import com.base.library.util.WDYLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作者：王东一 on 2015/12/22 09:29
 **/
public class DoPicCapUtil {
    public static final String IMAGE_FILE_LOCATION = Environment.getExternalStorageDirectory() + "/cache/camera/temp.png";
    public static final String DATA = "data";
    public static final String MENU_STATE = "MENUSTATE";// menuactivity返回值2:相机，3：相册
    public static final int REQUEST_CODE_MENU = 1;// menuactivity,相机、拍照、取消
    public static final int REQUEST_CODE_MENU2 = 4;// menuactivity2,删除、查看、取消
    public static final int REQUEST_CODE_CAPTURE = 2;// 相机完成
    public static final int REQUEST_CODE_ALBUM = 3;// 相册完成
    public static final int REQUEST_CODE_DELETE = 5;// 删除
    public static final int REQUEST_CODE_LOOK = 6;// 查看
    public static final int CAMERA_CROP_DATA = 3022;
    /**
     * 图片处理：裁剪.
     */
    public static final int CUTIMG = 0;

    /**
     * 图片处理：缩放.
     */
    public static final int SCALEIMG = 1;

    /**
     * 图片处理：不处理.
     */
    public static final int ORIGINALIMG = 2;
    /**
     * 显示进度框.
     */
    public static final int SHOW_PROGRESS = 1;
    /**
     * 删除进度框.
     */
    public static final int REMOVE_PROGRESS = 2;

    public static void saveCameraBitmap(Bitmap bmp) {
        String path = Environment.getExternalStorageDirectory() + "/camera_cache";
        // 首先保存图片
        File appDir = new File(path);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "temp.jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static Bitmap getBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
        }
        return bitmap;
    }
    /**
     * type:1 拍照  2图库
     * data：返回数据
     * context 上下文
     * imageUri 图片地址
     * list 临时存储数组
     * templeposition 临时位置
     * strList 返回的字符串
     */
//	public static String getPicList(int type,Intent data,Context context,Uri imageUri,List<Map<String, Object>> list,int templeposition,List<String> strList){
//		switch (type) {
//		case 1:
////			return doCapture(data,context,imageUri,list,templeposition,strList);
//		case 2:
//			return doPicture(data,context);
//		}
//		return "";
//	}

//	/**
//	 * 拍照获取图片
//	 *
//	 * @param data
//	 * @return
//	 * @return
//	 */
//	private static String doCapture(Intent data,Context context,Uri imageUri,List<Map<String, Object>> list,int templeposition,List<String> strList) {
//		if (null == imageUri) {
//			ToastUtil.showShort(context, context.getString(R.string.capture_path_error));
//			return "";
//		}
//		if (imageUri.getPath() == null)
//			return "";
//		Bitmap bitmap = PictureUtil.getSmallBitmap(imageUri.getPath());
//		String picStream = PictureUtil.bitmapToString(bitmap);
//		// imagePath = PictureUtil.bitmapToString(bitmap);
//		String path = ImageUtil.getPackageName(context);
//		String name = ImageUtil.createFileName();
//		PictureUtil.save(bitmap, path + File.separator + name);
//		bitmap.recycle();
//		return addData(path + File.separator + name, picStream,list,templeposition,strList);
//	}

    /**
     * 图库选择图片
     *
     * @param data
     */
    public static String doPicture(Intent data, Context context) {
        if (data == null || data.getData() == null) {
            CodeUtil.showToastShort(context, "选择图片文件出错");
            return "";
        }
        Uri uri = data.getData();// 照片的原始资源地址
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(proj[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
//			Bitmap bitmap = PictureUtil.getSmallBitmap(filePath);
//			if(bitmap==null){
//				ToastUtil.showShort(context, "无缩略图！");
//				return strList;
//			}
//			String stream = PictureUtil.bitmapToString(bitmap);
//			// imagePath = PictureUtil.bitmapToString(bitmap);
//			String path = ImageUtil.getPackageName(context);
//			String name = ImageUtil.createFileName();
//			PictureUtil.save(bitmap, path + File.separator + name);
//			bitmap.recycle();
//			return addData(path + File.separator + name, stream,list,templeposition,strList);
        }
        return "";
    }

    public static List<String> addData(String url, String stream, List<Map<String, Object>> list, int templeposition, List<String> strList) {
        if (templeposition == -1)
            return null;
        list.get(templeposition).put("url", url);
        if (list.size() < 4) {// 控制最大数据为4条
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("url", null);
            list.add(map);
        }
        if (strList.size() < 4) {
            strList.add(stream);
        }
        return strList;
    }

    /**
     * @param
     * @return
     * @Description 新增图片
     */
    public static void openCapture(Intent data, Context context, Uri imageUri) {
        Intent mIntent = null;
        int states = data.getExtras().getInt(MENU_STATE);
        // 判断SD卡是否存在
        if (!CodeUtil.getSDStatus(context))
            return;
        if (states == DoPicCapUtil.REQUEST_CODE_CAPTURE) {
            // 调用相机
            mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 指定拍摄照片保存路径
            mIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            ((Activity) context).startActivityForResult(mIntent, DoPicCapUtil.REQUEST_CODE_CAPTURE);
        } else {
            // 调用android的图库
            mIntent = new Intent(Intent.ACTION_GET_CONTENT);
            mIntent.setType("image/*");
            ((Activity) context).startActivityForResult(mIntent, DoPicCapUtil.REQUEST_CODE_ALBUM);
        }
    }

    /**
     * @param
     * @return
     * @Description 查看和删除
     */
    public static List<Map<String, Object>> lookDeletePic(Intent data, Context context, int templeposition, List<Map<String, Object>> list) {
        int statess = data.getExtras().getInt(MENU_STATE);
        if (statess == DoPicCapUtil.REQUEST_CODE_DELETE) {// 删除
            if (list.size() == 4 && list.get(3).get("url") != null) {// 添加一条空数据用于添加图片
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("url", null);
                list.add(map);
            }
            list.remove(templeposition);
            deleteTemp(String.valueOf(list.get(templeposition)));
            return list;
        } else {// 查看
            Intent intent = new Intent(context, HotPictureViewActivity.class);
            ArrayList<String> array = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).get("url") != null)
                    array.add(list.get(i).get("url").toString());
            }
            intent.putStringArrayListExtra(DATA, array);
            intent.putExtra("pos", templeposition);
            context.startActivity(intent);
        }
        return list;
    }

    /**
     * @param
     * @return
     * @Description 删除文件
     */
    public static void deleteTemp(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     * <p/>
     * imagesrc
     *
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    /**
     * Bitmap对象转换Drawable对象.
     *
     * @param bitmap 要转化的Bitmap对象
     * @return Drawable 转化完成的Drawable对象
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        BitmapDrawable mBitmapDrawable = null;
        try {
            if (bitmap == null) {
                return null;
            }
            mBitmapDrawable = new BitmapDrawable(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mBitmapDrawable;
    }

    /**
     * 描述：获取src中的图片资源.
     *
     * @param src 图片的src路径，如（“image/arrow.png”）
     * @return Bitmap 图片
     */
    public static Bitmap getBitmapFormSrc(String src) {
        Bitmap bit = null;
        try {
            bit = BitmapFactory.decodeStream(DoPicCapUtil.class.getResourceAsStream(src));
        } catch (Exception e) {
            Log.d("裁剪图片", "获取图片异常：" + e.getMessage());
        }
        return bit;
    }

    /**
     * 描述：裁剪图片.
     *
     * @param bitmap    the bitmap
     * @param newWidth  新图片的宽
     * @param newHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap cutImg(Bitmap bitmap, int newWidth, int newHeight) {
        if (bitmap == null) {
            return null;
        }

        if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("裁剪图片的宽高设置不能小于0");
        }

        Bitmap resizeBmp = null;

        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            if (width <= 0 || height <= 0) {
                return null;
            }
            int offsetX = 0;
            int offsetY = 0;

            if (width > newWidth) {
                offsetX = (width - newWidth) / 2;
            } else {
                newWidth = width;
            }

            if (height > newHeight) {
                offsetY = (height - newHeight) / 2;
            } else {
                newHeight = height;
            }

            resizeBmp = Bitmap.createBitmap(bitmap, offsetX, offsetY, newWidth, newHeight);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resizeBmp != bitmap) {
                bitmap.recycle();
            }
        }
        return resizeBmp;
    }

    /**
     * 描述：裁剪图片.
     *
     * @param file      File对象
     * @param newWidth  新图片的宽
     * @param newHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap cutImg(File file, int newWidth, int newHeight) {
        Bitmap resizeBmp = null;
        if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("裁剪图片的宽高设置不能小于0");
        }

        BitmapFactory.Options opts = new BitmapFactory.Options();
        //设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), opts);
        //inSampleSize=2表示图片宽高都为原来的二分之一，即图片为原来的四分之一
        //缩放可以将像素点打薄,裁剪前将图片缩放到目标图2倍大小
        int srcWidth = opts.outWidth;  // 获取图片的原始宽度
        int srcHeight = opts.outHeight;// 获取图片原始高度
        int destWidth = 0;
        int destHeight = 0;

        int cutSrcWidth = newWidth * 2;
        int cutSrcHeight = newHeight * 2;

        // 缩放的比例,为了大图的缩小到2倍被裁剪的大小在裁剪
        double ratio = 0.0;
        //任意一个不够长就不缩放
        if (srcWidth < cutSrcWidth || srcHeight < cutSrcHeight) {
            ratio = 0.0;
            destWidth = srcWidth;
            destHeight = srcHeight;
        } else if (srcWidth > cutSrcWidth) {
            ratio = (double) srcWidth / cutSrcWidth;
            destWidth = cutSrcWidth;
            destHeight = (int) (srcHeight / ratio);
        } else if (srcHeight > cutSrcHeight) {
            ratio = (double) srcHeight / cutSrcHeight;
            destHeight = cutSrcHeight;
            destWidth = (int) (srcWidth / ratio);
        }

        //默认为ARGB_8888.
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        //以下两个字段需一起使用：
        //产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
        opts.inPurgeable = true;
        //位图可以共享一个参考输入数据(inputstream、阵列等)
        opts.inInputShareable = true;
        // 缩放的比例，缩放是很难按准备的比例进行缩放的，通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
        if (ratio > 1) {
            opts.inSampleSize = (int) ratio;
        } else {
            opts.inSampleSize = 1;
        }
        // 设置大小
        opts.outHeight = destHeight;
        opts.outWidth = destWidth;
        //创建内存
        opts.inJustDecodeBounds = false;
        //使图片不抖动
        opts.inDither = false;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), opts);
        if (bitmap != null) {
            resizeBmp = cutImg(bitmap, newWidth, newHeight);
        }
        return resizeBmp;
    }

    /**
     * 描述：根据等比例缩放图片.
     *
     * @param bitmap the bitmap
     * @param scale  比例
     * @return Bitmap 新图片
     */
    public static Bitmap scaleImg(Bitmap bitmap, float scale) {
        Bitmap resizeBmp = null;
        try {
            //获取Bitmap资源的宽和高
            int bmpW = bitmap.getWidth();
            int bmpH = bitmap.getHeight();
            //注意这个Matirx是android.graphics底下的那个
            Matrix mt = new Matrix();
            //设置缩放系数，分别为原来的0.8和0.8
            mt.postScale(scale, scale);
            resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bmpW, bmpH, mt, true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resizeBmp != bitmap) {
                bitmap.recycle();
            }
        }
        return resizeBmp;
    }

    /**
     * 描述：缩放图片.压缩
     *
     * @param file      File对象
     * @param newWidth  新图片的宽
     * @param newHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap scaleImg(File file, int newWidth, int newHeight) {
        Bitmap resizeBmp = null;
        if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("缩放图片的宽高设置不能小于0");
        }
        BitmapFactory.Options opts = new BitmapFactory.Options();
        //设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), opts);

        // 获取图片的原始宽度高度
        int srcWidth = opts.outWidth;
        int srcHeight = opts.outHeight;

        int destWidth = srcWidth;
        int destHeight = srcHeight;

        // 缩放的比例
        float scale = 0;
        // 计算缩放比例，使一个方向缩放后，另一方向不小与显示的大小的最合适比例
        float scaleWidth = (float) newWidth / srcWidth;
        float scaleHeight = (float) newHeight / srcHeight;
        if (scaleWidth > scaleHeight) {
            scale = scaleWidth;
        } else {
            scale = scaleHeight;
        }
        if (scale != 0) {
            destWidth = (int) (destWidth / scale);
            destHeight = (int) (destHeight / scale);
        }

        //默认为ARGB_8888.
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        //以下两个字段需一起使用：
        //产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
        opts.inPurgeable = true;
        //位图可以共享一个参考输入数据(inputstream、阵列等)
        opts.inInputShareable = true;

        //inSampleSize=2 表示图片宽高都为原来的二分之一，即图片为原来的四分之一
        // 缩放的比例，SDK中建议其值是2的指数值，通过inSampleSize来进行缩放，其值表明缩放的倍数
        if (scale > 1) {
            //缩小
            opts.inSampleSize = (int) scale;
        } else {
            //不缩放
            opts.inSampleSize = 1;
        }

        // 设置大小
        opts.outHeight = destHeight;
        opts.outWidth = destWidth;
        //创建内存
        opts.inJustDecodeBounds = false;
        //使图片不抖动
        opts.inDither = false;
        //if(D) Log.d(TAG, "将缩放图片:"+file.getPath());
        resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
        //缩小或者放大
        if (resizeBmp != null && scale != 1) {
            resizeBmp = scaleImg(resizeBmp, scale);
        }
        //if(D) Log.d(TAG, "缩放图片结果:"+resizeBmp);
        return resizeBmp;
    }

    /**
     * 描述：获取原图.
     *
     * @param file File对象
     * @return Bitmap 图片
     */
    public static Bitmap originalImg(File file) {
        Bitmap resizeBmp = null;
        try {
            resizeBmp = BitmapFactory.decodeFile(file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resizeBmp;
    }

    /**
     * 描述：通过文件的本地地址从SD卡读取图片.
     *
     * @param file      the file
     * @param type      图片的处理类型（剪切或者缩放到指定大小，参考AbConstant类）
     *                  如果设置为原图，则后边参数无效，得到原图
     * @param newWidth  新图片的宽
     * @param newHeight 新图片的高
     * @return Bitmap 新图片
     */
    public static Bitmap getBitmapFromSD(File file, int type, int newWidth, int newHeight) {
        Bitmap bit = null;
        try {
            //SD卡是否存在
            if (!CodeUtil.sdCardIsAvailable()) {
                return null;
            }

            if (type != ORIGINALIMG && (newWidth <= 0 || newHeight <= 0)) {
                throw new IllegalArgumentException("缩放和裁剪图片的宽高设置不能小于0");
            }

            //文件是否存在
            if (!file.exists()) {
                return null;
            }

            //文件存在
            if (type == CUTIMG) {
                bit = cutImg(file, newWidth, newHeight);
            } else if (type == SCALEIMG) {
                bit = scaleImg(file, newWidth, newHeight);
            } else {
                bit = originalImg(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getBitmapDegree(file.getAbsolutePath()) != 0) {
            bit = rotateBitmapByDegree(bit, getBitmapDegree(file.getAbsolutePath()));
        }
        return bit;
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    public static Bitmap makeRoundCorner(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int left = 0, top = 0, right = width, bottom = height;
        float roundPx = height / 2;
        if (width > height) {
            left = (width - height) / 2;
            top = 0;
            right = left + height;
            bottom = height;
        } else if (height > width) {
            left = 0;
            top = (height - width) / 2;
            right = width;
            bottom = top + width;
            roundPx = width / 2;
        }
        WDYLog.i("放转圆", "ps:" + left + ", " + top + ", " + right + ", " + bottom);
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(left, top, right, bottom);
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
