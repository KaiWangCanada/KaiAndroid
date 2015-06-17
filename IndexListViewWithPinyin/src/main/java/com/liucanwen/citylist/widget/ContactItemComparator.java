package com.liucanwen.citylist.widget;

import com.liucanwen.citylist.model.IndexListItem;

import java.util.Comparator;

public class ContactItemComparator implements Comparator<IndexListItem>
{

	@Override
	public int compare(IndexListItem lhs, IndexListItem rhs)
	{
		if (lhs.getItemForIndex() == null || rhs.getItemForIndex() == null)
			return -1;

		return (lhs.getItemForIndex().compareTo(rhs.getItemForIndex()));

	}

}
