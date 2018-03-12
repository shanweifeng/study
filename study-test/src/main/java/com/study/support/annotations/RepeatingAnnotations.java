package com.study.support.annotations;

import java.lang.annotation.*;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 18:37 2018/3/12
 * @Modified By:
 */
public class RepeatingAnnotations {
    @Target( ElementType.TYPE )
    @Retention( RetentionPolicy.RUNTIME )
    public @interface Filters {
        Filter[] value();
    }

    @Target( ElementType.TYPE )
    @Retention( RetentionPolicy.RUNTIME )
    @Repeatable( Filters.class )
    public @interface Filter {
        String value();
        };

    @RepeatingAnnotations.Filter( "filter1" )
    @RepeatingAnnotations.Filter( "filter2" )
    public interface Filterable {
    }

    public static void main(String[] args) {
        for( Filter filter: Filterable.class.getAnnotationsByType( Filter.class ) ) {
            System.out.println( filter.value() );
        }
    }
}
