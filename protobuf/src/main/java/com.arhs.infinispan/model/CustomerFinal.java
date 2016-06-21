package com.arhs.infinispan.model;

import org.infinispan.protostream.annotations.ProtoDoc;
import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoMessage;

import java.io.Serializable;

/***
 * Customer persistence object
 */
@ProtoDoc("@Indexed")
@ProtoMessage(name = "CustomerFinal")
public class CustomerFinal implements Serializable {
    private String customerId;
    private String customerGroupId;

    public CustomerFinal() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @ProtoDoc("@IndexedField")
    @ProtoField(number = 1)
    public String getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(String customerGroupId) {
        this.customerGroupId = customerGroupId;
    }
}
