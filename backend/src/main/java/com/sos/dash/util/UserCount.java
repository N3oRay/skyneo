package com.sos.dash.util;

import com.sos.obs.decc.domain.Center;

public class UserCount {
    private Center center;
    private Long count;

    public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Center getCenter() {
		return center;
	}

	public void setCenter(Center center) {
		this.center = center;
	}


	public UserCount(Center center, Long count) {
        this.center = center;
        this.count = count;
    }


}

