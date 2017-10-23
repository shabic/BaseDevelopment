package com.doomsday.base.utils;

import com.doomsday.base.annotation.ContentView;
import com.doomsday.base.ui.BaseUi;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by DoomsDay on 2017/8/16.
 */

public class PresenterUtils {

    /**
     * 初始化presenter
     *
     * @param view new XxxPresenter(this)中的this，用于创建p的参数
     * @param <P>
     */
    public static <P> P initPresenter(BaseUi view) {
        Class aClass = view.getClass();
        if (!(aClass.getGenericSuperclass() instanceof ParameterizedType)) return null;
        ParameterizedType genericSuperclass = (ParameterizedType) aClass.getGenericSuperclass();
        if (genericSuperclass == null) return null;
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        if (actualTypeArguments == null || actualTypeArguments.length == 0) return null;
        Class<P> pClass = (Class<P>) actualTypeArguments[0];
        try {
            Constructor<P> constructor = pClass.getConstructor(aClass.getInterfaces()[0]);
            return   constructor.newInstance(view);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取class上的ContentView的值（LayoutId）
     *
     * @param obj this
     * @return
     */
    public static int getLayoutId(BaseUi obj) {
        Class<? extends BaseUi> aClass = obj.getClass();
        if (aClass.isAnnotationPresent(ContentView.class)) {
            ContentView annotation = aClass.getAnnotation(ContentView.class);
            return annotation.value();
        }
        return 0;
    }
}
