package com.joshuarichardson.fivewaystowellbeing.ui.inspire;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.joshuarichardson.fivewaystowellbeing.ActivityTypeImageHelper;
import com.joshuarichardson.fivewaystowellbeing.R;
import com.joshuarichardson.fivewaystowellbeing.WaysToWellbeing;
import com.joshuarichardson.fivewaystowellbeing.WellbeingHelper;
import com.joshuarichardson.fivewaystowellbeing.ui.insights.WayToWellbeingImageColorizer;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InspireAdapter extends RecyclerView.Adapter<InspireAdapter.InspireActivityViewHolder>  implements Filterable {
    private final LayoutInflater inflater;
    private final InspireClickListener clickListener;
    private final Context context;
    public List<InspireRecord> originalInspireRecords = new ArrayList<>();
    private List<InspireRecord> inspireListItems = new ArrayList<>();

    public InspireAdapter(Context context, List<InspireRecord> inspirationList, InspireAdapter.InspireClickListener clickListener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.originalInspireRecords.addAll(inspirationList);
        this.inspireListItems.addAll(inspirationList);
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public InspireAdapter.InspireActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.inspire_feed_item, parent, false);

        return new InspireActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InspireActivityViewHolder holder, int position) {
        holder.onBind(this.inspireListItems.get(position));
    }

    @Override
    public int getItemCount() {
        return this.inspireListItems.size();
    }

    public void setValues(List<InspireRecord> inspireData, String searchTerm) {
        this.originalInspireRecords.clear();
        this.originalInspireRecords.addAll(inspireData);
        getFilter().filter(searchTerm);
    }

    @Override
    public Filter getFilter() {
        return searchInspireFilter;
    }

    // Filter is used to allow the user to search for an item
    Filter searchInspireFilter = new Filter() {

        @Override
        // Reference https://www.tutorialspoint.com/how-to-filter-a-recyclerview-with-a-searchview-on-android
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            // Filter should always start with way to wellbeing
            int index = constraint.toString().indexOf("$");
            if(index == -1) {
                results.values = InspireAdapter.this.originalInspireRecords;
                return results;
            }

            String wayToWellbeing = constraint.toString().substring(0, index);
            String query = constraint.toString().substring(index + 1);

            List<InspireRecord> filteredInspireRecords = new ArrayList<>();

            // If there is a search pattern this will find all of the items
            String filterPattern = query.toString().toLowerCase().trim();
            filterPattern = filterPattern.replaceAll("[^A-Za-z0-9 ]", "");
            for (InspireRecord record : InspireAdapter.this.originalInspireRecords) {
                String listItemName = record.getActivityName().toLowerCase();
                listItemName = listItemName.replaceAll("[^A-Za-z0-9 ]", "");
                if (listItemName.matches("([\\s\\w]*\\s" + filterPattern + "[\\s\\w]*)|(^" + filterPattern + "[\\s\\w]*)")) {
                    if (wayToWellbeing.equals(WaysToWellbeing.UNASSIGNED.toString())) {
                        filteredInspireRecords.add(record);
                    } else if (record.getActivityWayToWellbeing().equals(wayToWellbeing)) {
                        filteredInspireRecords.add(record);
                    }
                }
            }

            results.values = filteredInspireRecords;
            return results;
        }

        @Override
        // Reference: https://www.tutorialspoint.com/how-to-filter-a-recyclerview-with-a-searchview-on-android
        protected void publishResults(CharSequence constraint, FilterResults results) {
            InspireAdapter.this.inspireListItems.clear();

            InspireAdapter.this.inspireListItems.addAll((List) results.values);

            // ToDo - this is needed to add wilbur images
//            int thing = InspireAdapter.this.inspireListItems.size();
//
//            if (thing >= 5) {
//                for (int i = 4; i < thing; i += 5) {
//                    thing ++;
//                    inspireListItems.add(i, new InspireRecord(0, "", ActivityType.HOBBY.toString(), WaysToWellbeing.TAKE_NOTICE.toString(), 5, false, InspireType.Image));
//                }
//            } else {
//                inspireListItems.add(thing, new InspireRecord(0, "", ActivityType.HOBBY.toString(), WaysToWellbeing.TAKE_NOTICE.toString(), 5, false, InspireType.Image));
//            }

            // Update the recycler view
            notifyDataSetChanged();
        }
    };

    public class InspireActivityViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView wayToWellbeingTextView;
        private final TextView typeTextView;
        private final ImageView image;
        private final FrameLayout frame;
        private final ImageButton favouriteButton;
        private final View inspireSuggestion;
        private final View wilburImage;

        public InspireActivityViewHolder(@NonNull View itemView) {
            super(itemView);

            this.nameTextView = itemView.findViewById(R.id.app_name_text_view);
            this.wayToWellbeingTextView = itemView.findViewById(R.id.way_to_wellbeing_text_view);
            this.typeTextView = itemView.findViewById(R.id.associated_activity_text_view);
            this.image = itemView.findViewById(R.id.list_item_image);
            this.frame = itemView.findViewById(R.id.icon_image_frame);
            this.favouriteButton = itemView.findViewById(R.id.favorite_button);
            this.inspireSuggestion = itemView.findViewById(R.id.inspire_suggestion);
            this.wilburImage = itemView.findViewById(R.id.wilbur_image);
        }

        public void onBind(InspireRecord inspireRecord) {
            this.nameTextView.setText(inspireRecord.getActivityName());
            this.typeTextView.setText(String.format("%s%s", inspireRecord.getActivityType().substring(0, 1), inspireRecord.getActivityType().substring(1).toLowerCase()));

            if (inspireRecord.getInspireType() == InspireType.Image) {
                this.image.setImageResource(0);
                WayToWellbeingImageColorizer.colorizeFrame(InspireAdapter.this.context, this.frame, WaysToWellbeing.UNASSIGNED);
                this.inspireSuggestion.setVisibility(View.GONE);
                this.wilburImage.setVisibility(View.VISIBLE);
                ImageView image = this.wilburImage.findViewById(R.id.wilbur_image_item);
                image.setImageURI(null);
                image.setImageURI(Uri.parse("https://adventures-of-wilbur-images.s3.eu-west-2.amazonaws.com/WP_20160601_20_38_09_Pro.jpg"));
            } else if (inspireRecord.getInspireType() == InspireType.Text) {
                this.inspireSuggestion.setVisibility(View.VISIBLE);
                this.wilburImage.setVisibility(View.GONE);
                WayToWellbeingImageColorizer.colorizeFrame(InspireAdapter.this.context, this.frame, WaysToWellbeing.valueOf(inspireRecord.getActivityWayToWellbeing()));

                if (inspireRecord.getActivityWayToWellbeing().equals("UNASSIGNED")) {
                    this.wayToWellbeingTextView.setVisibility(View.GONE);
                } else {
                    this.wayToWellbeingTextView.setText(WellbeingHelper.getStringFromWayToWellbeing(WaysToWellbeing.valueOf(inspireRecord.getActivityWayToWellbeing())));
                    this.wayToWellbeingTextView.setVisibility(View.VISIBLE);
                }

                if (inspireRecord.isFavourite()) {
                    favouriteButton.setImageResource(R.drawable.favorite_button_selected);
                } else {
                    favouriteButton.setImageResource(R.drawable.favorite_button_unselected);
                };

                this.image.setImageResource(ActivityTypeImageHelper.getActivityImage(inspireRecord.getActivityType()));

                // Reference https://medium.com/android-gate/recyclerview-item-click-listener-the-right-way-daecc838fbb9
                this.favouriteButton.setOnClickListener((v) -> {
                    inspireRecord.isFavourite(!inspireRecord.isFavourite());
                    clickListener.onFavouriteButtonClicked(v, inspireRecord);

                    if (inspireRecord.isFavourite()) {
                        favouriteButton.setImageResource(R.drawable.favorite_button_selected);
                    } else {
                        favouriteButton.setImageResource(R.drawable.favorite_button_unselected);
                    };

                });
            }
        }
    }

    public interface InspireClickListener {
        void onFavouriteButtonClicked(View v, InspireRecord inspireRecord);
    }
}