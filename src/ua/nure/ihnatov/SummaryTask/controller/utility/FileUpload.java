package ua.nure.ihnatov.SummaryTask.controller.utility;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import ua.nure.ihnatov.SummaryTask.controller.dao.exception.DAOException;
import ua.nure.ihnatov.SummaryTask.controller.services.ProductService;
import ua.nure.ihnatov.SummaryTask.model.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FileUpload {
    private static final String UPLOAD_LOCATION = "upload.location";

    public boolean actionWithProduct(HttpServletRequest request) throws DAOException, IOException, ServletException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            return false;
        }
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            boolean flag = false;
            boolean isUpdated = false;
            List<FileItem> items = upload.parseRequest(request);
            List<String> paramList = new ArrayList<>();
            Product product = null;
            String fileName = null;
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    fileName = item.getName();
                    paramList.add(fileName);
                } else if (Fields.PRODUCT_ID.equals(item.getFieldName())) {
                    flag = true;
                    paramList.add(item.getString(String.valueOf(StandardCharsets.UTF_8)));
                } else {
                    paramList.add(item.getString(String.valueOf(StandardCharsets.UTF_8)));
                }
            }
            if (flag) {
                isUpdated = updateUser(paramList);
            } else {
                product = createUser(paramList);
            }
            if (product != null || isUpdated) {
                if (!items.isEmpty()) {
                    for (FileItem item : items) {
                        if (!item.isFormField()) {
                            Properties properties = new Properties();
                            InputStream io = this.getClass().getResourceAsStream(Paths.PROPERTIES);
                            properties.load(io);
                            final String path = properties.getProperty(UPLOAD_LOCATION);
                            if (fileName == null) {
                                return false;
                            }
                            File uploadedFile = new File(path + "/" + fileName);
                            if (uploadedFile.exists()) {
                                FileUtils.forceDelete(uploadedFile);
                                uploadedFile = new File(path, fileName);
                            }
                            item.write(uploadedFile);
                        }
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
        return false;
    }

    public Product createUser(List<String> paramList) throws DAOException {
        return new ProductService().create(getProductWithSetParam(paramList));
    }

    public boolean updateUser(List<String> paramList) throws DAOException {
        final int idPosition = 7;
        Long id = Long.valueOf(paramList.get(idPosition));
        Product product = getProductWithSetParam(paramList);
        product.setId(id);
        return new ProductService().update(product);
    }

    private Product getProductWithSetParam(List<String> paramList) {
        int k = 0;
        String name = paramList.get(k++);
        String type = paramList.get(k++);
        Double price = Double.valueOf(paramList.get(k++));
        String capacity = paramList.get(k++);
        String color = paramList.get(k++);
        String currency = paramList.get(k++);
        String imageName = paramList.get(k);
        return new Product(name, type, capacity, color, price, currency, imageName);
    }
}
