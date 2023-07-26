package com.xk.util;

import com.xk.util.sm2.SM2SignVO;
import com.xk.util.sm2.SM2SignVerUtils;
import com.xk.util.sm3.Util;
import com.xk.util.sm4.SM4Utils;
//import net.sf.json.JSONObject;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.DERSequence;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TtSmUtil {

//    public static void main(String[] args) throws Exception {
//        //验签返回Boolean值
////        Map b = signSM2("30450220676840debc53e9a7e70af3eeba574ffd4d7ab9127e2617e138d112376c7c3432022100dc3ff95d255eaf169d6aecf5377dcb83728e9e31032be417d1025693fa4353af");
////        boolean b = signSM2("08800420a16a0599668d21d8fbb1c31262a0263ae0d8492a06bc5f5b3f1df93c8c57d7e726906944b347030040935b1cb14f9a0e763da28df933c64179d97092ac6054fbe90612295051f13308afd1ed6a3d0a2eab6aedc2432d2f77772c408a568f58b93bbe45deb437d03210658440bed09d4afa6b9a905fcac4e691b68835cc77bcd2bccf164141397193b9d574a3f059d50794082fa06f68fff4931bf1dd6d3d90242c4b614b88cb972ab0f734508853988a45f0ccf6eb5bfbc35db4ca6591ad8ba5330fe6364faca3b94bd75f8fc3ba48ffcc79769f736ce7b63f525476a534919ac355c3571838eca024c17b417b6f71549e910ccaa3088a4446ba1127");
////        System.out.println(b);
////        String src = "ererfeiisgod";
//        String sm4Key = "c44d56590c9a428fa4ec59a7245e7870";
////        System.out.println("--SM4的CBC加密--");
//////        String s3 = SM4EncForCBC(sm4Key, src);
//        String s3 = "3909dc5af46b8a30fb0dd9938d48bb041788462288a972d66780ff4e29042a13ca2de0e926f4a0e217db8db2d4b1f57b121d2e54e95ed254356df2c0cae507530d0cbe3f445d8abf5475249137613cd64bc61dec59d9484bfb279b4ac4c58eade29d3076a264fc5468abae2628e6dc03d56820783b6cd2161d07fa4867ed31062eec68e6fcde4083c4260eccc47ba0ed7675933b0de450be731668543b89a31f52eba51f8a09abe630888a071c37bf0df9c6d6f63de116848fb54363b3af2149c071e7f4a26c911b05dafc2447bb6e1b5806343c4686b9967515edcde5868f10cfadab783f6142430dca6d6c8bacdb64836ce20699b599d2d8c49253c2c3127ea46ca49d0a2d43a5604947fec17a34f39096a0bd5eff06c25c5c598bee672a43e80aa364c67f1d74dae4fa6c4f0bfd4cb741de11189c7bf38ecd0a37bb24f544825a330b8781bddc220bac8e903969a542fc0521f98e7354fc6594c893ed9259dbc9f9d687e6cc553fc0cafb3396057ba61ed3428547395e54412897570a62cf6f335c012958027bcb72c66206ea42e1649c6734dc1c8b669dcb472798eb9f2bab35622e0204d82ee574e00060bf7333862101c2f87075b7086dcce7a9cc24eadfe4b893b74672afdd7e155c1a812a86696c30c8d5dc4a075617b704c08988fc0c9924a9e817bc1490487c79262132285e3803aba33dbdad6306a1a7f9155dbd990675caf3e4862a959dbcd00fc6a04045729450606d434ed5e34e0e64b927dca05bb6683dc3e3d978bcc93ee05d59e3093690af896eaa4f4f08c513cb2cfa4c12e83f091572e5168fe8c3553f6cb846f93f8e764bbf924853553bb0e1a96ed3fc36cedf7402375bacec1da05b6fa672391da48b23621fe3e2c892ca9f7174a72443f13752f2a909806cfef5c70a7aa2afce4e7aa63adff1dc6bf58f48c8bb9da57bd43cab29742998aa9148bdf0136b8af3fb4da515022a52e9c714190e6c794a528100935f52ae04b62a0165ad7b3c5116991544e4187342bb6478468eb6ea16ce33dbb48e46c9721c5874822e406d19f4b06e1d0b2f77afec7291c9f11fd2b9a0327e256b6d9ca80e7e4af74270a9128d5bb00a2f0edb07b601f31f04d778f3410eff2bacb6c100aebc874f65b01746e75ed7fbfc9d682d1d61e8d3f38e9d8e151c1805ea3b0037d39d301690a0a2c6c4c46a9374589e35651b7a4c57462497195aae39be8236ade8ec26990ea8d60edbde74e0c75c40e389ee85ebb6bfb490a7b5a7f9ea87c981fb0e34bca5f14a";
////        String s3 = "08800420a16a0599668d21d8fbb1c31262a0263ae0d8492a06bc5f5b3f1df93c8c57d7e726906944b347030040935b1cb14f9a0e763da28df933c64179d97092ac6054fbe90612295051f13308afd1ed6a3d0a2eab6aedc2432d2f77772c408a568f58b93bbe45deb437d03210658440bed09d4afa6b9a905fcac4e691b68835cc77bcd2bccf164141397193b9d574a3f059d50794082fa06f68fff4931bf1dd6d3d90242c4b614b88cb972ab0f734508853988a45f0ccf6eb5bfbc35db4ca6591ad8ba5330fe6364faca3b94bd75f8fc3ba48ffcc79769f736ce7b63f525476a534919ac355c3571838eca024c17b417b6f71549e910ccaa3088a4446ba1127";
//        System.out.println("密文:"+s3);
//        System.out.println("--SM4的CBC解密--");
//        String s2 = SM4DecForCBC(sm4Key, s3);
//        System.out.println("明文:"+s2);
//    }

   /* {
        "transCode":"IFPP101",
            "verNo":"V100",
            "channelId":"B1005840",
            "srcReqDate":"20160811",
            "srcReqTime":"182102",
            "srcReqId":"0000000002",
            "signature":"08800420a16a0599668d21d8fbb1c31262a0263ae0d8492a06bc5f5b3f1df93c8c57d7e726906944b347030040935b1cb14f9a0e763da28df933c64179d97092ac6054fbe90612295051f13308afd1ed6a3d0a2eab6aedc2432d2f77772c408a568f58b93bbe45deb437d03210658440bed09d4afa6b9a905fcac4e691b68835cc77bcd2bccf164141397193b9d574a3f059d50794082fa06f68fff4931bf1dd6d3d90242c4b614b88cb972ab0f734508853988a45f0ccf6eb5bfbc35db4ca6591ad8ba5330fe6364faca3b94bd75f8fc3ba48ffcc79769f736ce7b63f525476a534919ac355c3571838eca024c17b417b6f71549e910ccaa3088a4446ba1127",
            "stdmerno":"13898202000100142",
            "stdlegalname":"杜智倩",
            "stdlegalctype":"1",
            "stdlegalcno":"520102198909025426",
            "stdregno":"203666001001031",
            "stdrichid":"000109475318",
            "stdmerserno":"2c4507114ad748188797005584856c38"
    }*/

    //对称秘钥解密(CBC)
    public static String SM4DecForCBC(String key,String text) {
        SM4Utils sm4 = new SM4Utils();
        sm4.secretKey = key;
        sm4.hexString = true;
        sm4.iv = "31313131313131313131313131313131";
        String plainText = sm4.decryptData_CBC(text);
        return plainText;
    }
    //对称秘钥加密(CBC)
    public static String SM4EncForCBC(String key,String text) {
        SM4Utils sm4 = new SM4Utils();
        sm4.secretKey = key;
        sm4.hexString = true;
        sm4.iv = "31313131313131313131313131313131";
        String cipherText = sm4.encryptData_CBC(text);
        return cipherText;
    }
    public static Map signSM2(String signature) throws Exception{
        String pubHexInSoft="04ed3dddd93fa7881d248da39ef943660131e7c6eb67de7d8089d697460c484638df1d8d90e285002a8d6d2fcd18511d01656ab760ef5916a0503bba74ff16adc6";
        String priHexInSoft="279164def6779d099903d2e4b1c4a514c5add48d008abbbfb371a1de11e2dd09";
        SM2SignVO sign = genSM2Signature(priHexInSoft, signature);
        System.out.println("签名结果:" + sign.getSm2_signForSoft());
        String b = verifySM2Signature(pubHexInSoft, signature, sign.getSm2_signForSoft());
        Map map =new HashMap();
        map.put("bool",b);
        map.put("ret_signature",sign.getSm2_signForSoft());
        return map;
    }

    //公钥验签,参数二:原串必须是hex!!!!因为是直接用于计算签名的,可能是SM3串,也可能是普通串转Hex
    public static String verifySM2Signature(String pubKey, String sourceData, String hardSign) {
        SM2SignVO verify = SM2SignVerUtils.VerifySignSM2(Util.hexStringToBytes(pubKey), Util.hexToByte(sourceData), Util.hexToByte(hardSign));
        return String.valueOf(verify.isVerify());
    }


    //私钥签名,参数二:原串必须是hex!!!!因为是直接用于计算签名的,可能是SM3串,也可能是普通串转Hex
    public static SM2SignVO genSM2Signature(String priKey, String sourceData) throws Exception {
        SM2SignVO sign = SM2SignVerUtils.Sign2SM2(Util.hexToByte(priKey), Util.hexToByte(sourceData));
        return sign;
    }


    //SM2签名Hard转soft
    public static String SM2SignHardToSoft(String hardSign) {
        byte[] bytes = Util.hexToByte(hardSign);
        byte[] r = new byte[bytes.length / 2];
        byte[] s = new byte[bytes.length / 2];
        System.arraycopy(bytes, 0, r, 0, bytes.length / 2);
        System.arraycopy(bytes, bytes.length / 2, s, 0, bytes.length / 2);
        ASN1Integer d_r = new ASN1Integer(Util.byteConvertInteger(r));
        ASN1Integer d_s = new ASN1Integer(Util.byteConvertInteger(s));
        ASN1EncodableVector v2 = new ASN1EncodableVector();
        v2.add(d_r);
        v2.add(d_s);
        DERSequence sign = new DERSequence(v2);

        String result = null;
        try {
            result = Util.byteToHex(sign.getEncoded());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        //SM2加密机转软加密编码格式
        //return SM2SignHardKeyHead+hardSign.substring(0, hardSign.length()/2)+SM2SignHardKeyMid+hardSign.substring(hardSign.length()/2);
        return result;
    }

    public static void main(String[] args) throws JSONException {
        String a="{user_code=17683931049, user_pass=e10adc3949ba59abbe56e057f20f883e}";
        JSONObject jsonObject=new JSONObject(a);
        Map map=new HashMap();
        map=JsonMap.jsonToMap(jsonObject.toString());
        System.out.println(map);
    }
}
