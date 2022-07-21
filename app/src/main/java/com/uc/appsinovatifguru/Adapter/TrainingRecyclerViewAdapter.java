package com.uc.appsinovatifguru.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;

import com.uc.appsinovatifguru.EvalActivity;
import com.uc.appsinovatifguru.MainActivity;
import com.uc.appsinovatifguru.Model.Training;
import com.uc.appsinovatifguru.QuizActivity;
import com.uc.appsinovatifguru.R;
import com.uc.appsinovatifguru.TestActivity;
import com.uc.appsinovatifguru.TrainingActivity;

import java.util.ArrayList;

public class TrainingRecyclerViewAdapter extends RecyclerView.Adapter<TrainingRecyclerViewAdapter.EvalRecyclerViewHolder> {
    private ArrayList<Training> listTraining;
    private Lifecycle lifecycle;

    public TrainingRecyclerViewAdapter(ArrayList<Training> listTraining, Lifecycle lifecycle) {
        this.listTraining = listTraining;
        this.lifecycle = lifecycle;
    }

    @NonNull
    @Override
    public EvalRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == R.layout.itemrv_trainingperkenalan) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemrv_trainingperkenalan, parent, false);
        } else if (viewType == R.layout.itemrv_trainingconsent) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemrv_trainingconsent, parent, false);
        } else if (viewType == R.layout.itemrv_trainingpertemuan) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemrv_trainingpertemuan, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemrv_trainingtest, parent, false);
        }

        return new EvalRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EvalRecyclerViewHolder holder, int position) {
        if (holder.getItemViewType() == R.layout.itemrv_trainingperkenalan) {
            holder.itemTrainingPerkenalanJudulTextView.setText(listTraining.get(position).getJudul());
            if (listTraining.get(position).getProfilPeneliti() != null) {
                holder.itemTrainingPerkenalanProfilTextView.setText(listTraining.get(position).getProfilPeneliti());
            } else {
                holder.itemTrainingPerkenalanProfilTextView.setText(listTraining.get(position).getTemplateWord());
            }
        } else if (holder.getItemViewType() == R.layout.itemrv_trainingconsent) {
            holder.itemTrainingConsentUploadPdfButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("application/pdf");

                    ((Activity) view.getContext()).startActivityForResult(intent, 2);
                }
            });
        } else if (holder.getItemViewType() == R.layout.itemrv_trainingpertemuan) {
            holder.itemTrainingPertemuanUploadPdfButton.setVisibility(View.GONE);
            holder.itemTrainingPertemuanJudulTextView.setText(listTraining.get(position).getJudul());
//            holder.itemTrainingPertemuanDownloadPdfButton.setText(listTraining.get(position).getDownloadPdf());
            if (listTraining.get(position).getUploadPdf() != null) {
                holder.itemTrainingPertemuanUploadPdfButton.setVisibility(View.VISIBLE);
                holder.itemTrainingPertemuanUploadPdfButton.setText(listTraining.get(position).getUploadPdf());
            }

            holder.itemTrainingPertemuanWebView.getSettings().setJavaScriptEnabled(true);
            holder.itemTrainingPertemuanWebView.setWebChromeClient(new WebChromeClient());
            String embed = "<iframe width=\"100%\" height=\"100%\" src=\"" + listTraining.get(position).getVideo() + "\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
            holder.itemTrainingPertemuanWebView.loadData(embed, "text/html", "utf-8");

            holder.itemTrainingPertemuanUploadPdfButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("application/pdf");

                    ((Activity) view.getContext()).startActivityForResult(intent, 2);
                }
            });
        } else {
            holder.itemTrainingTestJudulTextView.setText(listTraining.get(position).getJudul());
            String buttonText = "Kerjakan " + listTraining.get(position).getJudul();
            holder.itemTrainingTestEvaluasiButton.setText(buttonText);

            holder.itemTrainingTestEvaluasiButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listTraining.get(holder.getAdapterPosition()).isEval()) {
                        Intent intent1 = new Intent(view.getContext(), EvalActivity.class);
                        intent1.putExtra("variabel", "Intensi Berinovasi");
                        view.getContext().startActivity(intent1);
                    } else {
                        Intent intent1 = new Intent(view.getContext(), TestActivity.class);
                        intent1.putExtra("variabel", "Intensi Berinovasi");
                        view.getContext().startActivity(intent1);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listTraining.size();
    }

    @Override
    public int getItemViewType(int position) {
        int id;

        if (listTraining.get(position).getProfilPeneliti() != null) {
            id = R.layout.itemrv_trainingperkenalan;
        } else if (listTraining.get(position).getTemplateWord() != null) {
            id = R.layout.itemrv_trainingconsent;
        } else if (listTraining.get(position).getDownloadPdf() != null) {
            id = R.layout.itemrv_trainingpertemuan;
        } else {
            id = R.layout.itemrv_trainingtest;
        }

        return id;
    }

    public static class EvalRecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTrainingPerkenalanJudulTextView;
        private TextView itemTrainingPerkenalanProfilTextView;

        private Button itemTrainingConsentUploadPdfButton;

        private TextView itemTrainingPertemuanJudulTextView;
        private Button itemTrainingPertemuanDownloadPdfButton;
        private Button itemTrainingPertemuanUploadPdfButton;
        WebView itemTrainingPertemuanWebView;

        private TextView itemTrainingTestJudulTextView;
        private Button itemTrainingTestEvaluasiButton;


        public EvalRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTrainingPerkenalanJudulTextView = itemView.findViewById(R.id.itemTrainingPerkenalanJudulTextView);
            itemTrainingPerkenalanProfilTextView = itemView.findViewById(R.id.itemTrainingPerkenalanProfilTextView);

            itemTrainingConsentUploadPdfButton = itemView.findViewById(R.id.itemTrainingConsentUploadPdfButton);

            itemTrainingPertemuanJudulTextView = itemView.findViewById(R.id.itemTrainingPertemuanJudulTextView);
//            itemTrainingPertemuanDownloadPdfButton = itemView.findViewById(R.id.itemTrainingPertemuanDownloadPdfButton);
            itemTrainingPertemuanUploadPdfButton = itemView.findViewById(R.id.itemTrainingPertemuanUploadPdfButton);
            itemTrainingPertemuanWebView = itemView.findViewById(R.id.itemTrainingPertemuanWebView);

            itemTrainingTestJudulTextView = itemView.findViewById(R.id.itemTrainingTestJudulTextView);
            itemTrainingTestEvaluasiButton = itemView.findViewById(R.id.itemTrainingTestEvaluasiButton);
        }
    }
}