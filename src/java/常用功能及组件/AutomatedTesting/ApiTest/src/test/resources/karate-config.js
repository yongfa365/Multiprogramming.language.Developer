function fn() {
    var env = karate.env; // get java system property 'karate.env'
    // A common requirement is to pass dynamic parameter values via the command line,
    // and you can use the karate.properties['some.name'] syntax for getting a system property
    // passed via JVM options in the form -Dsome.name=foo.
    // mvn test -DargLine="-Dkarate.env=dev"
    karate.log('karate.env system property was:', env);
    if (!env) {
        env = 'test'; // a custom 'intelligent' default
    }
    var config = {
        baseUrl: 'http://127.0.0.1:8120',
        env: env,
    };
    if (env === 'dev') {
        config.baseUrl = 'http://127.0.0.1:8120';
    } else if (env === 'test') {
        config.baseUrl = 'http://127.0.0.1:8120';
    } else if (env === 'uat') {
        config.baseUrl = 'http://127.0.0.1:8120';
    } else if (env === 'prod') {
        config.baseUrl = 'http://127.0.0.1:8120';
    }
    // Set the connect timeout (milliseconds). The default is 30000 (30 seconds).
    //karate.configure('connectTimeout', 5000);
    //Set the read timeout (milliseconds). The default is 30000 (30 seconds).
    karate.configure('readTimeout', 60000);
    //enable ssl (and no certificate is required)
    karate.configure('ssl', true)
    karate.configure('ssl', {trustAll: true});
    karate.configure('report', {showLog: true, showAllSteps: false});


    return config;
}