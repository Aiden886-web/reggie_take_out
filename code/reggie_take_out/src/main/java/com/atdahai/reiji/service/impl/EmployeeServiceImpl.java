package com.atdahai.reiji.service.impl;

import com.atdahai.reiji.entity.Employee;
import com.atdahai.reiji.mapper.EmployeeMapper;
import com.atdahai.reiji.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper , Employee> implements EmployeeService {
    
}
