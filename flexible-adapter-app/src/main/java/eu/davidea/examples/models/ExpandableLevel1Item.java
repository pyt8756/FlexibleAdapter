package eu.davidea.examples.models;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.examples.flexibleadapter.R;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IExpandable;
import eu.davidea.viewholders.ExpandableViewHolder;

/**
 * This is an experiment to evaluate how a Section with header can also be expanded/collapsed.
 * <p>Here, it still benefits of the common fields declared in AbstractExampleItem.</p>
 * It's important to note that, the ViewHolder must be specified in all &lt;diamond&gt; signature.
 */
public class ExpandableLevel1Item
		extends AbstractExampleItem<ExpandableLevel1Item.ViewHolder>
		implements IExpandable<ExpandableLevel1Item.ViewHolder, SubItem> {

	private static final long serialVersionUID = -1882711111814491060L;

	/* Flags for FlexibleAdapter */
	private boolean mExpanded = false;

	/* subItems list */
	private List<SubItem> mSubItems;


	public ExpandableLevel1Item(String id) {
		super(id);
		setSelectable(false);
	}

	@Override
	public boolean isExpanded() {
		return mExpanded;
	}

	@Override
	public void setExpanded(boolean expanded) {
		mExpanded = expanded;
	}

	@Override
	public int getExpansionLevel() {
		return 1;
	}//This allows +1 level of expansion

	@Override
	public List<SubItem> getSubItems() {
		return mSubItems;
	}

	public final boolean hasSubItems() {
		return mSubItems!= null && mSubItems.size() > 0;
	}

	public boolean removeSubItem(SubItem item) {
		return item != null && mSubItems.remove(item);
	}

	public boolean removeSubItem(int position) {
		if (mSubItems != null && position >= 0 && position < mSubItems.size()) {
			mSubItems.remove(position);
			return true;
		}
		return false;
	}

	public void addSubItem(SubItem subItem) {
		if (mSubItems == null)
			mSubItems = new ArrayList<SubItem>();
		mSubItems.add(subItem);
	}

	public void addSubItem(int position, SubItem subItem) {
		if (mSubItems != null && position >= 0 && position < mSubItems.size()) {
			mSubItems.add(position, subItem);
		} else
			addSubItem(subItem);
	}

	@Override
	public int getLayoutRes() {
		return R.layout.recycler_expandable_row;
	}

	@Override
	public ViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
		return new ViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
	}

	@Override
	public void bindViewHolder(FlexibleAdapter adapter, ViewHolder holder, int position, List payloads) {
		if (payloads.size() > 0) {
			Log.i(this.getClass().getSimpleName(), "ExpandableHeaderItem Payload " + payloads);
		} else {
			holder.mTitle.setText(getTitle());
		}
		setSubtitle(adapter.getCurrentChildren(this).size() + " subItems");
		holder.mSubtitle.setText(getSubtitle());
	}

	/**
	 * Provide a reference to the views for each data item.
	 * Complex data labels may need more than one view per item, and
	 * you provide access to all the views for a data item in a view holder.
	 */
	public static class ViewHolder extends ExpandableViewHolder {

		public TextView mTitle;
		public TextView mSubtitle;

		public ViewHolder(View view, FlexibleAdapter adapter) {
			super(view, adapter);
			mTitle = (TextView) view.findViewById(R.id.title);
			mSubtitle = (TextView) view.findViewById(R.id.subtitle);
		}

		@Override
		protected boolean isViewExpandableOnClick() {
			return true;
		}
	}

	@Override
	public String toString() {
		return "ExpandableHeaderItem[" + super.toString() + "//SubItems" + mSubItems + "]";
	}

}