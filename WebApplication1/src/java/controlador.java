
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class controlador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<body>");
            String recibido = "";
            int nrpp = 2;
            int inicio = 0;
            int fin = 0;
            int nrt = 7;
            int np = (int) (nrt / nrpp);
            int aux = np * nrpp;
            if (nrt != aux) {
                np++;
            }
            // out.println("np" + np + "<br>");
            recibido = request.getParameter("p");
            int actual = 0;
            actual = Integer.parseInt(recibido.trim());
            // out.println("var1 = " + recibido + "<br>");
            if (actual > 1) {
                out.println("<a href='http://localhost:8080/WebApplication1/controlador?p=" + (actual - 1) + "'>Anterior</a>");
            }
            for (int i = 1; i <= np; i++) {
                out.println("<a href='http://localhost:8080/WebApplication1/controlador?p=" + i + "'>" + i + "</a>");
            }
            fin = nrpp * actual;
            inicio = fin - nrpp + 1;
            if (actual == np) {
                fin = nrt;
            }
            if (actual < np) {
                out.println("<a href='http://localhost:8080/WebApplication1/controlador?p=" + (actual + 1) + "'>Siguiente</a><br>");
            }
            // out.println("<br>INICIO " + inicio + " ");
            // out.println("FIN " + fin + "<br>");
            
            try {                
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/luis", "root", "");
                Statement stmt = (Statement) con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from estudiante where codigo>="+inicio+" and codigo<="+fin);
                while (rs.next()) {
                    out.println(rs.getInt(1) + "  " + rs.getString(2));
                }
                con.close();
            } catch (Exception e1) {
                out.println(e1);
            }
            
            out.println("</body>");
            out.println("</html>");
            
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
