package academylab.shoppingservice.service;
import academylab.shoppingservice.entity.Invoice;
import academylab.shoppingservice.repository.InvoiceItemRepository;
import academylab.shoppingservice.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService{

  @Autowired
  InvoiceRepository invoiceRepository;

  @Autowired
  InvoiceItemRepository invoiceItemRepository;


  @Override
  public List<Invoice> findInvoiceAll() {
    return  invoiceRepository.findAll();
  }

  @Override
  public Invoice updateInvoice(Invoice invoice) {
    Invoice invoiceDb = getInvoice(invoice.getId());
    if(invoiceDb == null){
      return null;
    }
    invoiceDb.setCustomerId(invoice.getCustomerId());
    invoiceDb.setDescription(invoice.getDescription());
    invoiceDb.setNumberInvoice(invoice.getNumberInvoice());
    invoiceDb.getItems().clear();
    invoiceDb.setItems(invoice.getItems());
    return invoiceRepository.save(invoiceDb);
  }

  @Override
  public Invoice createInvoice(Invoice invoice) {
    Invoice invoiceDb = invoiceRepository.findByNumberInvoice(invoice.getNumberInvoice());
    if(invoiceDb != null){
      return invoiceDb;
    }
    invoice.setState("CREATED");
    return invoiceRepository.save(invoice);
  }

  @Override
  public Invoice deleteInvoice(Invoice invoice) {
    Invoice invoiceDb = invoiceRepository.findByNumberInvoice(invoice.getNumberInvoice());
    if(invoiceDb == null){
      return null;
    }
    invoiceDb.setState("DELETED");
    return invoiceRepository.save(invoiceDb);
  }

  public Invoice getInvoice(Long id){
    return invoiceRepository.findById(id).orElse(null);
  }
}
