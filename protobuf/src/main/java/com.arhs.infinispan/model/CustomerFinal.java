package com.arhs.infinispan.model;

import org.infinispan.protostream.annotations.ProtoDoc;
import org.infinispan.protostream.annotations.ProtoField;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/***
 * Customer persistence object
 */
@ProtoDoc("@Indexed")
public class CustomerFinal implements Serializable {
    private String customerId;
    private String customerGroupId;

    public CustomerFinal() {
    }

    @ProtoDoc("@IndexedField(index=true, store=true)")
    @ProtoField(number = 1)
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @ProtoDoc("@IndexedField(index=true, store=true)")
    @ProtoField(number = 2, required = false, javaType = String.class)
    public String getCustomerGroupId() {
        return customerGroupId;
    }


    public void setCustomerGroupId(String customerGroupId) {
        this.customerGroupId = customerGroupId;
    }
}
