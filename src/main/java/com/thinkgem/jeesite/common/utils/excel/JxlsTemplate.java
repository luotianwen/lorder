package com.thinkgem.jeesite.common.utils.excel;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;

/**
 * JXSL excel模板工具方法
 * <br/>
 * JXSL detail please see http://jxls.sourceforge.net/<br/>
 * User: Foy Lian
 * Date: 2017-07-04
 * Time: 17:44
 */
public class JxlsTemplate {
    /**
     * 模板路径
     */
    private static final String TEMPLATE_DIR = "/excel";

    protected static Log logger = LogFactory.getLog(JxlsTemplate.class);

    /**
     * 使用JxlsTemplate.class.getResourceAsStream load 模板
     *
     * @param template 模板名称，相当于TEMPLATE_DIR设置的路径
     * @param out      生成excel写入的输出流
     * @param params   交给jxls处理模板需要的参数
     * @throws IOException
     */
    public static void processTemplate(String template, OutputStream out, Map<String, ?> params) throws Exception {
        processTemplate(JxlsTemplate.class, template, out, params);
    }

    /**
     * 使用resourceBaseClassgetResourceAsStream load 模板
     *
     * @param resourceBaseClass class load的类
     * @param template          模板名称
     * @param out               生成excel写入的输出流
     * @param params            交给jxls处理模板需要的参数
     * @throws IOException
     */
    public static void processTemplate(Class resourceBaseClass, String template, OutputStream out, Map<String, ?> params) throws Exception {
        InputStream in = resourceBaseClass.getResourceAsStream(TEMPLATE_DIR + template);
        if (null == in) {
            logger.error("can't find excel template by path:" + TEMPLATE_DIR + template);
            throw new Exception("找不到excel模板！,位置:" + TEMPLATE_DIR + template);
        }
        processTemplate(in, out, params);
    }

    /**
     * @param templateStream excel模板流
     * @param out            生成excel写入的输出流
     * @param context        jxsl上下文
     * @throws IOException
     */
    private static void processTemplate(InputStream templateStream, OutputStream out, Context context) throws IOException {
       /* JxlsHelper jxlsHelper = JxlsHelper.getInstance();
        Transformer transformer  = jxlsHelper.createTransformer(templateStream, out);
        //获得配置
        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator)transformer.getTransformationConfig().getExpressionEvaluator();
        //设置静默模式，不报警告
        evaluator.getJexlEngine().setSilent(true);
        //函数强制，自定义功能
        Map<String, Object> funcs = new HashMap<String, Object>();
        funcs.put("utils", new JxlsUtils());    //添加自定义功能
        evaluator.getJexlEngine().setFunctions(funcs);
        //必须要这个，否者表格函数统计会错乱
        jxlsHelper.setUseFastFormulaProcessor(false).processTemplate(context, transformer);*/
        JxlsHelper.getInstance().processTemplate(templateStream, out, context);
    }

    /**
     * @param templateStream excel模板流
     * @param out            生成excel写入的输出流
     * @param params         交给jxls处理模板需要的参数
     * @throws IOException
     */
    public static void processTemplate(InputStream templateStream, OutputStream out, Map<String, ?> params) throws IOException {
        Context context = new Context();
        if (params != null) {
            for (String key : params.keySet()) {
                context.putVar(key, params.get(key));
            }
        }
        processTemplate(templateStream, out, context);
    }
}

