package com.uc.appsinovatifguru.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import com.uc.appsinovatifguru.GlobalValue;
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
            if (listTraining.get(position).getType().equals("pertemuansubmit")) {
                holder.itemTrainingPertemuanUploadPdfButton.setVisibility(View.VISIBLE);
            }

            holder.itemTrainingPertemuanWebView.getSettings().setJavaScriptEnabled(true);
            holder.itemTrainingPertemuanWebView.setWebChromeClient(new WebChromeClient());
            String embed = "<iframe width=\"100%\" height=\"100%\" src=\"" + listTraining.get(position).getLink() + "\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
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

            if (listTraining.get(position).getStatus() == 1) {
                holder.itemTrainingTestDoneTextView.setVisibility(View.VISIBLE);
            } else {
                holder.itemTrainingTestDoneTextView.setVisibility(View.GONE);
            }

            holder.itemTrainingTestEvaluasiButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listTraining.get(holder.getAdapterPosition()).getType().equals("eval")) {
                        Intent intent = new Intent(view.getContext(), EvalActivity.class);
                        intent.putExtra("id", listTraining.get(holder.getAdapterPosition()).getId());
                        view.getContext().startActivity(intent);
                    } else {
                        Intent intent = new Intent(view.getContext(), TestActivity.class);
                        intent.putExtra("id", listTraining.get(holder.getAdapterPosition()).getId());
                        if (listTraining.get(holder.getAdapterPosition()).getType().equals("pretest")) {
                            intent.putExtra("tipe", "pretest");
                        } else {
                            intent.putExtra("tipe", "posttest");
                        }
                        view.getContext().startActivity(intent);
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

        switch (listTraining.get(position).getType()) {
            case "perkenalan":
                id = R.layout.itemrv_trainingperkenalan;
                break;
            case "consent":
                id = R.layout.itemrv_trainingconsent;
                break;
            case "pertemuan":
            case "pertemuansubmit":
                id = R.layout.itemrv_trainingpertemuan;
                break;
            default:
                id = R.layout.itemrv_trainingtest;
                break;
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
        private TextView itemTrainingTestDoneTextView;
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
            itemTrainingTestDoneTextView = itemView.findViewById(R.id.itemTrainingTestDoneTextView);
            itemTrainingTestEvaluasiButton = itemView.findViewById(R.id.itemTrainingTestEvaluasiButton);
        }
    }
}
