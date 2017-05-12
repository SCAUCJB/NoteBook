package scau.edu.cn.notebook.impl;

/**
 * 作用：NoteModel接口
 */
public interface NoteModelImpl {

         void  getSliderShowNote(BaseListener listener);

    interface BaseListener<T>{
             void getSuccess(T t);
             void getFailure();
        }
}
