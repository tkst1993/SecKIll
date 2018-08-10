package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SecKillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.SecKill;
import org.seckill.enums.SecKillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillCloseException;
import org.seckill.service.SecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by tangke on 2016/7/1.
 */
@Controller
@RequestMapping(value="/seckill",method = RequestMethod.GET)//url:模块/资源/{id}/细分
public class SeckillController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SecKillService seckillService;
    @RequestMapping("/list")
    public String list(Model model){
        List<SecKill> list =  seckillService.getSecKillList();
        model.addAttribute("list",list);
        return "list";
    }
    @RequestMapping(value="/{seckillId}/detail",method= RequestMethod.GET)
    public String detail(@PathVariable("seckillId")Long seckillId,Model model){
        if(seckillId == null){
            return "redirect:/seckill/list";
        }
        SecKill secKill = seckillService.getById(seckillId);
        if(secKill == null){
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill",secKill);
        return "detail";
    }

    /**
     * ajax接口
     */
    @RequestMapping(value="/{seckillId}/exposer",
            method=RequestMethod.POST,
            produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public SeckillResult<Exposer> export(@PathVariable("seckillId")Long seckillId){
        SeckillResult<Exposer> res ;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            res = new SeckillResult<Exposer>(true,exposer);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            res = new SeckillResult<Exposer>(false,e.getMessage());
        }
        return res;

    }
    @RequestMapping(value = "/{seckillId}/{md5}/execution",method = RequestMethod.POST)
    @ResponseBody
    public SeckillResult<SecKillExecution>execute(@PathVariable("seckillId")Long seckillId,
                                                  @PathVariable("md5")String md5,
                                                  @CookieValue(value = "killPhone",required = false) Long userPhone)
    {
        //spring valid
        if(userPhone == null){
            return new SeckillResult<SecKillExecution>(false,"未注册");
        }
        SeckillResult<SecKillExecution> res;
        try{
            SecKillExecution execution = seckillService.executeSecKillProcedure(seckillId,userPhone,md5);
            res =  new SeckillResult<SecKillExecution>(true,execution);
        }catch (RepeatKillException e2){
            SecKillExecution execution = new SecKillExecution(seckillId, SecKillStateEnum.REPEAT_KILL);
            res =  new SeckillResult<SecKillExecution>(true,execution);
        }catch(SecKillCloseException e1){
            SecKillExecution execution = new SecKillExecution(seckillId, SecKillStateEnum.END);
            res =  new SeckillResult<SecKillExecution>(true,execution);
        }catch(Exception e){
            SecKillExecution execution = new SecKillExecution(seckillId, SecKillStateEnum.INNER_ERROR);
            res =  new SeckillResult<SecKillExecution>(true,execution);

        }
        return res;

    }
    @RequestMapping(value="/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long>time(){
        Date now = new Date();
        return new SeckillResult(true,now.getTime());

    }

}
