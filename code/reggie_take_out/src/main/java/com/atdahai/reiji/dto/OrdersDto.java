package com.atdahai.reiji.dto;

import com.atdahai.reiji.entity.OrderDetail;
import com.atdahai.reiji.entity.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
