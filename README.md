This project is a MRE (minimal reproducible example) of back navigation issue in AnimatedNavHost.
It is caused by navController receiving back events even if AnimatedNavHost is not in composition.

The issue is showcased on the recording:
The app starts with `innerDestinationHost/firstRoute` as the initial destination.
From there we can navigate to `innerDestinationHost/secondRoute` and `firstRoute`.
We navigate to `innerDestinationHost/secondRoute` from which we can navigate to `firstRoute`.
We navigate to `firstRoute` and go back N times.
We go back to initial destination by going back and we try go close the app by going back.
But the app won't close unless we press back N+1 times.

![recording](recordings/animated-nav-host-back-issue.webm)
