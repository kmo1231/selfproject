package com.miok.common;

public class FileVO {
    private Integer fileno;		//글번호
    private String parentPK;	//부모 글번호
    private String filename;	//파일명
    private String realname;	//실제파일명
    private long filesize;		//파일 크기
    
    /**
     * 파일 크기를 정형화하기.
     * 원리는 로그(log)를 이용한 것으로 밑수가 10인 로그(상용로그)를 실행하면 10의 배수가 계산된다.
		byte, K-byte, M-Byte 등은 1024배씩 커지는 것으로 약 10의 3배수씩 커진다고 보면 된다.
		즉, 1 K byte = 1024 byte 이고 1024 byte 는 Log를 취하면 약 3(3.010)의 값이 나온다. 
		이 3은 10의 자릿수로 보면 된다 (1000으로 보면 0의 개수).
		예로, 파일 크기(filesize)가 2248 byte일 경우 로그를 취하면 약 3(3.351)이 나오고 
		단위 (1024의 로그값 3)로 나누면 1(1.11)이 계산 된다. 
		즉, 단위를 나타내는 문자열("KMGTPE")에서 첫 번째 K가 선택되게 된다.
		단위를 구했으면 단위에 맞게끔 파일 크기를 계산해 줘야 한다.
		단위값(1024)을 거듭제곱(power)으로 1을 계산하면 1024가 나오고 이 값을 실제 파일 크기 2248에 대하여 나누면 2.19가 계산된다.
		따라서 최종적으로 2.2 K byte로 표기되는 것이다.
     */
    public String size2String() {
        Integer unit = 1024;
        if (filesize < unit) {
            return String.format("(%d B)", filesize);
        }
        int exp = (int) (Math.log(filesize) / Math.log(unit));

        return String.format("(%.0f %s)", filesize / Math.pow(unit, exp), "KMGTPE".charAt(exp - 1));
    }
    
    public Integer getFileno() {
        return fileno;
    }

    public void setFileno(Integer fileno) {
        this.fileno = fileno;
    }

    public String getParentPK() {
        return parentPK;
    }
    
    public void setParentPK(String parentPK) {
        this.parentPK = parentPK;
    } 
    
    public String getFilename() {
        return filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public String getRealname() {
        return realname;
    }
    
    public void setRealname(String realname) {
        this.realname = realname;
    }
    
    public long getFilesize() {
        return filesize;
    }
    
    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }
    
}
