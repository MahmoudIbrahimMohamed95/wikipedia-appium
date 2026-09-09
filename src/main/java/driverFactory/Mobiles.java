package driverFactory;

public enum Mobiles {
    ANDROID{
        @Override
        public AbstractDriver getDriverFactory(){
            return new AndroidFactory();
        }
    };
    public abstract AbstractDriver getDriverFactory();
}
