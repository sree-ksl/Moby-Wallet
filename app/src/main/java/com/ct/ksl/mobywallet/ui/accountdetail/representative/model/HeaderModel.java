package com.ct.ksl.mobywallet.ui.accountdetail.representative.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by user on 2018. 5. 29..
 */

@Getter
@Setter
public class HeaderModel implements BaseModel {

    private String title;

    public HeaderModel(String title) {
        this.title = title;
    }

    @Override
    public ViewType getViewType() {
        return ViewType.HEADER;
    }

    public String getTitle() {
        return title;
    }
}
