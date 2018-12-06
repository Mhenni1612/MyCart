package com.example.wael.mycart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RecyclerFragment extends Fragment {

    FirebaseFirestore db;
    CollectionReference collProd;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    public Fragment newInstance(String cat) {
        Log.d("recycler","-----------------------creating new instance---------------------------");
        return new RecyclerFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        collProd = db.collection("/menu/UXWGaeXrMcSolwMULl9c/produits");
        Log.d("ProduitView","******************************Start**************************");
        final List<Produit> lprod = new ArrayList<>();

        Log.d("ProduitView", "******************************message****************************************");

        final View view = inflater.inflate(R.layout.recycler_view_fragment, container , false);
        recyclerView = view.findViewById(R.id.produit_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        Log.d("ProduitView", "******************************Start collecting data****************************************");

        collProd
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("ProduitView", "******************************success****************************************");
                                Log.d("****", document.getId() + " => " + document.getData());

                                Produit prod = document.toObject(Produit.class);
                                lprod.add(prod);
                                prod.setId(document.getId());
                                Log.d("fds", String.valueOf(lprod));
                                //docData.put(document.getId(),prod);
                            }
                            Log.d("ProduitView", "******************************data collected****************************************\n"+lprod);
                            recyclerViewAdapter = new RecyclerViewAdapter(lprod);
                            recyclerView.setAdapter(recyclerViewAdapter);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            return;
                        }
                    }
                });
        Log.d("ProduitView", "******************************Done****************************************");
        return view;
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

            private CardView mCardView;
            private TextView textMarque;
            private TextView textNom;
            private TextView textGt;
            private TextView textPrix;
            private ImageView imageProd;

        public RecyclerViewHolder(@NonNull View itemView) {

            super(itemView);
        }

         public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.card_view, container, false));

            imageProd = itemView.findViewById(R.id.image_produit);
            textMarque = itemView.findViewById(R.id.text_marque);
            textNom = itemView.findViewById(R.id.text_nom);
            textGt = itemView.findViewById(R.id.text_gt);
            textPrix = itemView.findViewById(R.id.text_prix);

        }

    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{
        private List<Produit> mlist;

        public RecyclerViewAdapter(List<Produit> list) {
            this.mlist = list;
        }

        public void setMlist(List<Produit> mlist) {
            this.mlist = mlist;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new RecyclerViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
            holder.textMarque.setText(mlist.get(position).getMarque());
            holder.textNom.setText(mlist.get(position).getNom());
            String a = (mlist.get(position).getGout()) + " - " + (mlist.get(position).getTaille());
            holder.textGt.setText(a);
            holder.textPrix.setText(setPrix(mlist.get(position).getPrix()));
            Picasso.get().load(mlist.get(position).convertImage()).into(holder.imageProd);
        }

        @Override
        public int getItemCount() {
            return mlist.size() ;
        }

        public String setPrix(double p){
            double a=p;
            int i= (int) a;
            a-=i;
            int l=String.valueOf(a).length();
            if(a!=0){
                if(l==4){
                    return String.valueOf(p)+"0  DT";
                }else if(l>4){
                    return String.valueOf(p)+"  DT";
                }
            }else{
                return String.valueOf(p)+"000  DT";
            }
            return String.valueOf(p)+"00  DT";
        }
    }


    public void refreshFragment(String cat){

        final List<Produit> lprod = new ArrayList<>();
        Log.d("FragmentRefrech", "******************************Start**********************************"+cat);

        collProd
                .whereEqualTo("sousCategorie",cat)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("FragmentRefrech","***************************task successful**************");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("FragmentRefrech", "******************************success****************************************");
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Produit prod = document.toObject(Produit.class);
                                lprod.add(prod);
                                prod.setId(document.getId());
                                Log.d("FragmentRefrech", String.valueOf(lprod));
                            }
                            Log.d("FragmentRefrech", "******************************data collected****************************************\n"+lprod);
                            recyclerViewAdapter.setMlist(lprod);
                            recyclerViewAdapter.notifyDataSetChanged();

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            return;
                        }
                    }
                });

    }


}
