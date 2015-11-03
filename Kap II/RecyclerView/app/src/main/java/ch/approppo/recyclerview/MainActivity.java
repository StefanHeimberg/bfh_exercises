package ch.approppo.recyclerview;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	public static final String DATAOBJECT_KEY = "dataobject.key";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		final MyAdapter adapter = new MyAdapter(createDataList());
		adapter.setItemClickListener(new MyAdapter.OnItemClickListener() {
			@Override public void onItemClick(View v, int position) {
				Intent intent = new Intent(MainActivity.this, DetailActivity.class);
				intent.putExtra(DATAOBJECT_KEY, adapter.getData(position));
				MainActivity.this.startActivity(intent);
			}
		});

		adapter.setItemLongClickListener(new MyAdapter.OnItemLongClickListener() {
			@Override public void onItemLongClick(View v, int position) {
				adapter.removeItem(position);
				adapter.notifyItemRemoved(position);
			}
		});

		recyclerView.setAdapter(adapter);
	}

	private List<DataObject> createDataList() {
		List<DataObject> list = new ArrayList<>();
		for (int i = 0 ; i < 1000 ; i++) {
			DataObject dataObject = new DataObject();
			if (i % 2 == 0) {
				dataObject.setSex(DataObject.Sex.FEMALE);
				dataObject.setName("Käthi " + i);
				dataObject.setAge(30);
			}
			else {
				dataObject.setSex(DataObject.Sex.MALE);
				dataObject.setName("Franz " + i);
				dataObject.setAge(45);
			}
			list.add(dataObject);
		}
		return list;
	}

	private static class MyAdapter extends RecyclerView.Adapter implements View.OnClickListener {

		private List<DataObject> mData;

		private OnItemClickListener mItemClickListener;

		private OnItemLongClickListener mItemLongClickListener;


		public MyAdapter(List<DataObject> data) {
			this.mData = data;
		}

		public DataObject getData(int position) {
			return mData.get(position);
		}

		public void setItemClickListener(OnItemClickListener itemClickListener) {
			this.mItemClickListener = itemClickListener;
		}

		public void setItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
			this.mItemLongClickListener = itemLongClickListener;
		}

		public void removeItem(int position){
			mData.remove(position);
		}

		@Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
			DataObject dataObject = mData.get(position);
			((MyViewHolder) holder).name.setText(dataObject.getName());
			((MyViewHolder) holder).sex.setText(dataObject.getSex().toString());
			((MyViewHolder) holder).age.setText(String.valueOf(dataObject.getAge()));
		}

		@Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
			view.setOnClickListener(this);
			return new MyViewHolder(view);
		}

		@Override public int getItemCount() {
			return mData.size();
		}

		@Override public void onClick(View v) {
		}

		private class MyViewHolder extends RecyclerView.ViewHolder {

			TextView name, sex, age;

			public MyViewHolder(final View itemView) {
				super(itemView);
				name = (TextView) itemView.findViewById(R.id.item_name);
				sex = (TextView) itemView.findViewById(R.id.item_sex);
				age = (TextView) itemView.findViewById(R.id.item_age);
				itemView.setOnClickListener(new View.OnClickListener() {
					@Override public void onClick(View v) {
						if (mItemClickListener != null) {
							mItemClickListener.onItemClick(v, getAdapterPosition());
						}
					}
				});

				itemView.setOnLongClickListener(new View.OnLongClickListener() {
					@Override public boolean onLongClick(View v) {
						if (mItemLongClickListener != null) {
							mItemLongClickListener.onItemLongClick(v, getAdapterPosition());
							return true;
						}
						return false;
					}
				});
			}
		}

		public interface OnItemClickListener {
			void onItemClick(View v, int position);
		}

		public interface OnItemLongClickListener {
			void onItemLongClick(View v, int position);
		}
	}
}
