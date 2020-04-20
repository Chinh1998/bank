/////////////////////////////////////////////////////////////////////////////
//
// © 2020 VNEXT TRAINING
//
/////////////////////////////////////////////////////////////////////////////

package com.example.demo.bean.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * [OVERVIEW] XXXXX.
 *
 * @author: (VNEXT) ChinhTQ
 * @version: 1.0
 * @History
 * [NUMBER]  [VER]     [DATE]          [USER]             [CONTENT]
 * --------------------------------------------------------------------------
 * 001       1.0       2020/04/16      (VNEXT) ChinhTQ       Create new
*/
@Entity
@Table(name = "type_transaction")
public class TypeTransactionEntity {

    @Id
    private Integer typeId;
    private String typeTrading;
    
    public Integer getTypeId() {
        return typeId;
    }
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
    public String getTypeTrading() {
        return typeTrading;
    }
    public void setTypeTrading(String typeTrading) {
        this.typeTrading = typeTrading;
    }
    
}
