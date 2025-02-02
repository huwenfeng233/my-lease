package com.bighu.web.admin.controller.apartment;



import com.bighu.common.result.Result;
import com.bighu.model.entity.LeaseTerm;
import com.bighu.web.admin.service.LeaseTermService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "租期管理")
@RequestMapping("/admin/term")
@RestController
public class LeaseTermController {

    @Autowired
    private LeaseTermService leaseTermService;
    @GetMapping("list")
    @Operation(summary = "查询全部租期列表")
    public Result<List<LeaseTerm>> listLeaseTerm() {
        List<LeaseTerm> leaseTerms = new ArrayList<LeaseTerm>;
        try {
            leaseTerms = leaseTermService.list();
        }
        catch (Exception e)
        {
            return Result.fail();
        }


        return Result.ok(leaseTerms);

    }

    @PostMapping("saveOrUpdate")
    @Operation(summary = "保存或更新租期信息")
    public Result saveOrUpdate(@RequestBody LeaseTerm leaseTerm) {
        leaseTermService.saveOrUpdate(leaseTerm);
        return Result.ok();
    }

    @DeleteMapping("deleteById")
    @Operation(summary = "根据ID删除租期")
    public Result deleteLeaseTermById(@RequestParam Long id) {

        LeaseTerm byId = leaseTermService.getById(id);
        if (byId != null)
        {
            leaseTermService.removeById(id);
            return Result.ok();
        }
        return Result.fail();

    }
}
