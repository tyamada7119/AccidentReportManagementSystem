自分の環境で行う際の留意点：

AccidentManageSystem.java内、register(),delete(),viewAll(),viewConditional()それぞれにおいて、
DriverManager.getConnection()メソッド内のMYSQL接続時のパスワードを
ローカル環境のものに変えてから実行すること