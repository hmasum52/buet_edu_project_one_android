package com.example.buet_edu_project_one_vmasum.Adapters;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buet_edu_project_one_vmasum.Activities.ProblemActivity;
import com.example.buet_edu_project_one_vmasum.DataBase.Problem;
import com.example.buet_edu_project_one_vmasum.DataBase.RunTimeDB;
import com.example.buet_edu_project_one_vmasum.R;
import com.example.buet_edu_project_one_vmasum.Utils.Constant;

import java.text.DateFormat;
import java.util.Date;

public class ProblemsAdapter extends RecyclerView.Adapter<ProblemsAdapter.ProblemViewHolder> {

    private Activity activity;

    public ProblemsAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProblemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.problems_adapter_view,parent,false);
        return new ProblemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProblemViewHolder holder, final int position) {
        String[] ans_typeDescription = {"Figure Board","Text","MCQ"};

        RunTimeDB db = RunTimeDB.getInstance();
        Problem problem = db.getProblems().get(position);
        String title = problem.getTitle();
        String author = "-by " + problem.getAuthor()+" on " + DateFormat.getDateTimeInstance().format(new Date(problem.getTimestamp()));
        String category = "Category : " +Constant.CATEGORIES[problem.getCategory()] ;
        String ansType = "Ans Type: " + ans_typeDescription[problem.getAns_type() ];
        String difficulty ="Difficulty: " +problem.getDifficulty();
        String series = "Series: " +problem.getSeries();

        holder.probTitle.setText(title);
        holder.cat_icon.setImageResource(Constant.CAT_ICONS[problem.getCategory()]);
        holder.probAuthor.setText(author);
        holder.probCategory.setText(category);
        ValueAnimator animator = ValueAnimator.ofFloat(0f,50f);
        animator.setDuration(1000);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float temp = (float)(animation.getAnimatedValue());
                int haha = (int) temp;
                holder.succssRateBar.setProgress(haha);
                holder.probSuccessRate.setText(haha+"%");
            }

        });
        holder.probSeries.setText(series);

        holder.probCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProblemActivity.class);
                intent.putExtra("problemId",position);
                intent.putExtra("titlePosition",holder.probTitle.getLeft());

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(holder.probTitle,"problemTitleTransition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity,
                       pairs);
                activity.startActivity(intent,options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return RunTimeDB.getInstance().getProblems().size();
    }

    public class ProblemViewHolder extends RecyclerView.ViewHolder {
         TextView probAuthor,probCategory,probTitle,probSuccessRate,probSeries;
         ImageView cat_icon ;
         ProgressBar succssRateBar;
         CardView probCardView ;
        public ProblemViewHolder(@NonNull View itemView) {
            super(itemView);
            probTitle = itemView.findViewById(R.id.probAdptrTitleId);
            cat_icon = itemView.findViewById(R.id.probAdptrCatIconId);
            probCategory = itemView.findViewById(R.id.probAdptrCategoryId);
            succssRateBar = itemView.findViewById(R.id.probAdptrSuccssRateBar);
            probSuccessRate = itemView.findViewById(R.id.probAdptrSuccssRate);
          //  probAnsType = itemView.findViewById(R.id.probAdptrAndTypeId);
          //  probDifficulty = itemView.findViewById(R.id.probAdptrDifficultyId);
            probSeries = itemView.findViewById(R.id.probAdptrSeriesId);
            probAuthor = itemView.findViewById(R.id.probAdptrAuthorId);
            probCardView = itemView.findViewById(R.id.probAdptrCardViewId);

        }
    }
}