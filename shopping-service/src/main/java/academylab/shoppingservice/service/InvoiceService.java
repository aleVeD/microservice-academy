package academylab.shoppingservice.service;

import academylab.shoppingservice.entity.Invoice;

import java.util.List;

public interface InvoiceService {
  List<Invoice> findInvoiceAll();
  Invoice updateInvoice(Invoice invoice);
  Invoice createInvoice(Invoice invoice);
  Invoice deleteInvoice(Invoice invoice);
  Invoice getInvoice(Long id);
}
