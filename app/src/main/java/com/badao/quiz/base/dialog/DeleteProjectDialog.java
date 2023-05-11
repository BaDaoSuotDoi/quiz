package com.badao.quiz.base.dialog;

import com.badao.quiz.model.Project;

public class DeleteProjectDialog extends BaseEditForm{
    private IListener iListener;
    public DeleteProjectDialog(Project project,IListener iListener){
        setTitle("Delete project");
        setInstruction("Do you want to delete project: "+project.getName());
        setLabelApply("DELETE");
        setHideEditText(true);
        setShowApply(true);
        this.iListener = iListener;
    }
    @Override
    public void onClickApply(String content) {
        iListener.onDelete();
    }

    public interface IListener{
        void onDelete();
    }
}
