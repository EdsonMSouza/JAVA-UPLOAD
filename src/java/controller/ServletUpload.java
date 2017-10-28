package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/upload")
public class ServletUpload extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ServletUpload() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        // Verifica o tipo multipart/form-data*/
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                // Parse do request
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

                /**
                 * Escreve o arquivo na pasta img, dentro do build
                 * C:\Users\seu_usuario\Documents\NetBeansProjects\UploadImagem\build\web\img
                 */
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        item.write(new File(request.getServletContext().getRealPath("/img") + File.separator + item.getName()));
                    }
                }
                request.setAttribute("message", "Arquivo carregado com sucesso");
                
                /**
                 * Aqui deve ser colocado o procedimento para gravar o nome do arquivo no banco
                 * O nome do arquivo postado deve ser recuperado com o método: item.getName()
                 */
            } catch (Exception ex) {
                request.setAttribute("message", "Upload de arquivo falhou devido a " + ex);
            }

        } else {
            request.setAttribute("message", "Apenas para upload de imagens");
        }

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

}
