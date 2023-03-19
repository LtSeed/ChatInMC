package ltseed.chatinmc.PlayerInteraction.GUI;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 Annotation used to indicate that a view should be enabled.
 Can be applied to a class or interface.
 @author LtSeed
 @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnabledView {
    //void enableView();
}