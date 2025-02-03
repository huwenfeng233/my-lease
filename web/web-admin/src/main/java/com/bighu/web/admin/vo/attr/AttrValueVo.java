package com.bighu.web.admin.vo.attr;

import com.bighu.model.entity.AttrValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Schema(description = "属性值")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttrValueVo extends AttrValue {

    @Schema(description = "对应的属性key_name")
    private String attrKeyName;
}
