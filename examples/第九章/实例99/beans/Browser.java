package 第九章.实例99.beans;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Browser extends HttpServlet
{
    protected HttpServletRequest request;
    protected HttpSession session;
    protected String userAgent;
    protected String company; // 公司名称
    protected String name; // 浏览器名称
    protected String version; // 版本信息
    protected String os; // 所使用的操作系统
    private Hashtable supportedLanguages; // Untersttzte Sprachen
    public Browser(HttpServletRequest request, HttpSession session)
    {
        this.initialize();
        this.request = request;
        this.session = session;
        this.setUserAgent(this.request.getHeader("User-Agent"));
        this.setCompany();
        this.setName();
        this.setVersion();
        this.setOs();
    }
    public void initialize()
    {
        this.supportedLanguages = new Hashtable(2);
        this.supportedLanguages.put("en", "");
        this.supportedLanguages.put("de", "");
    }
    public void setUserAgent(String httpUserAgent)
    {
        this.userAgent = httpUserAgent.toLowerCase();
    }
    
// 获取浏览器制造商的名称
    private void setCompany()
    {
        if (this.userAgent.indexOf("msie") > -1)
        {
            this.company = "Microsoft";
        }
        else if (this.userAgent.indexOf("opera") > -1)
        {
            this.company = "Opera Software";
        }
        else if (this.userAgent.indexOf("mozilla") > -1)
        {
        this.company = "Netscape Communications";
        }
        else
        {
        this.company = "unknown";
        }
    }
    public String getCompany()
    {
        return this.company;
    }

// 获取浏览器的名称
    private void setName()
    {
        if (this.company == "Microsoft")
        {
            this.name = "Microsoft Internet Explorer";
        }
        else if (this.company == "Netscape Communications")
        {
            this.name = "Netscape Navigator";
        }
        else if (this.company == "Operasoftware")
        {
            this.name = "Operasoftware Opera";
        }
        else
        {
            this.name = "unknown";
        }
    }
    public String getName()
    {
        return this.name;
    }

//获取浏览器的版本
    private void setVersion()
    {
        int tmpPos;
        String tmpString;
        if (this.company == "Microsoft")
        {
            String str = this.userAgent.substring(this.userAgent.indexOf("msie") + 5);
            this.version = str.substring(0, str.indexOf(";"));
        }
        else
        {
            tmpString = (this.userAgent.substring(tmpPos = (this.userAgent.indexOf("/")) + 1, tmpPos + this.userAgent.indexOf(" "))).trim();
            this.version = tmpString.substring(0, tmpString.indexOf(" "));
        }
    }
    public String getVersion()
    {
        return this.version;
    }

// 获取操作系统制造商的名称
    private void setOs()
    {
        if (this.userAgent.indexOf("win") > -1)
        {
            if (this.userAgent.indexOf("windows 95") > -1 || this.userAgent.indexOf("win95") > -1)
            {
                this.os = "Windows 95";
            }
            if (this.userAgent.indexOf("windows 98") > -1 || this.userAgent.indexOf("win98") > -1)
            {
                this.os = "Windows 98";
            }
            if (this.userAgent.indexOf("windows nt") > -1 || this.userAgent.indexOf("winnt") > -1)
            {
                this.os = "Windows NT";
            }
            if (this.userAgent.indexOf("win16") > -1 || this.userAgent.indexOf("windows 3.") > -1)
            {
                this.os = "Windows 3.x";
            }
        }
    }
    public String getOs()
    {
        return this.os;
    }
}
