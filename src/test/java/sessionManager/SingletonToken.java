package sessionManager;


public class SingletonToken {
    private static SingletonToken session=null;
    private String token;

    private SingletonToken(){
    }

    public static SingletonToken getInstance(){
        if (session == null)
            session= new SingletonToken();

        return session;
    }

    public void refreshToken(String generatedToken){
        token = generatedToken;
    }
}
