package com.idi.finance.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KpiGroup {
	private int groupId;
	private String groupName;
	private List<KpiChart> kpiCharts;

	public KpiGroup() {
		super();
	}

	public KpiGroup(int kpiGroupId, String kpiGroupName) {
		this.groupId = kpiGroupId;
		this.groupName = kpiGroupName;
	}

	public KpiGroup(int kpiGroupId) {
		this.groupId = kpiGroupId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<KpiChart> getKpiCharts() {
		return kpiCharts;
	}

	public void setKpiCharts(List<KpiChart> kpiCharts) {
		this.kpiCharts = kpiCharts;
	}

	public void addKpiCharts(KpiChart kpiChart) {
		if (kpiChart != null) {
			if (kpiCharts == null)
				kpiCharts = new ArrayList<>();

			if (!kpiCharts.contains(kpiChart)) {
				kpiCharts.add(kpiChart);
			}

			kpiChart.setKpiGroup(this);
		}
	}

	public void addKpiCharts(List<KpiChart> kpiCharts) {
		if (kpiCharts != null) {
			Iterator<KpiChart> iter = kpiCharts.iterator();
			while (iter.hasNext()) {
				addKpiCharts(iter.next());
			}
		}
	}

	@Override
	public String toString() {
		String out = groupId + "  " + groupName;
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof KpiGroup)) {
			return false;
		}

		KpiGroup kpiGroup = (KpiGroup) obj;
		try {
			if (groupId != kpiGroup.getGroupId()) {
				return false;
			}

			if (groupName == null) {
				if (kpiGroup.getGroupName() != null)
					return false;
			} else if (kpiGroup.getGroupName() == null) {
				return false;
			} else {
				return groupName.trim().equalsIgnoreCase(kpiGroup.getGroupName().trim());
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
