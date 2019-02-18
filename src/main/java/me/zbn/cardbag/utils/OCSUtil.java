package me.zbn.cardbag.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.*;


/**
 * @author: zhangbaoning
 * @date: 2019/2/18
 * @since: JDK 1.8
 * @description: 腾讯对象存储工具
 */
public class OCSUtil {
    /**
     * 1 传入获取到的临时密钥 (tmpSecretId, tmpSecretKey, sessionToken)
     */
    BasicSessionCredentials cred = new BasicSessionCredentials("a-demo-secretId", "a-demo-secretKey", "a-demo-session-token");
    /**
     * 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
     * clientConfig 中包含了设置 region, https(默认http), 超时, 代理等 set 方法, 使用可参见源码或者接口文档 FAQ
     */

    ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing-1"));
    /**
     *  3 生成 cos 客户端
     */
    COSClient cosClient = new COSClient(cred, clientConfig);
    /**
     *  bucket 的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
     */
    String bucketName = "mybucket-1251668577";

    public void upload(byte[] fileByte) {
            File localFile = new File("src/test/resources/len5M.txt");
        // 指定要上传到的存储桶
        String bucketName = "demoBucket-1250000000";
        // 指定要上传到 COS 上对象键
        String key = "upload_single_demo.txt";
        ByteArrayInputStream stream = new ByteArrayInputStream(fileByte);
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, stream,null);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        cosClient.shutdown();
    }

    public void download() {
        // 指定要下载到的本地路径
        File downFile = new File("src/test/resources/mydown.txt");
        // 指定文件所在的存储桶
        String bucketName = "demoBucket-1250000000";
        // 指定文件在 COS 上的对象键
        String key = "upload_single_demo.txt";

        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);
        cosClient.shutdown();
    }

    public void delete() {
        // 指定文件所在的存储桶
        String bucketName = "demoBucket-1250000000";
        // 指定文件在 COS 上的对象键
        String key = "upload_single_demo.txt";
        cosClient.deleteObject(bucketName, key);
        cosClient.shutdown();
    }
}
