{{- define "sandbox-chart.deployment" }}
kind: Deployment
apiVersion: apps/v1
metadata:
  name: {{.Values.ComponentName}}
  namespace: default
  generation: 2
  labels:
    k8s-app: {{.Values.ComponentName}}
spec:
  replicas: 1
  selector:
    matchLabels:
      k8s-app: {{.Values.ComponentName}}
  template:
    metadata:
      name: {{.Values.ComponentName}}
      labels:
        k8s-app: {{.Values.ComponentName}}
    spec:
      containers:
        - name: {{.Values.ComponentName}}
          image: 'akvone/sandbox-{{.Values.ComponentName}}:1.0-SNAPSHOT'
          resources: { }
          terminationMessagePath: /dev/termination-log
          terminationMessagePolicy: File
          imagePullPolicy: Always
          securityContext:
            privileged: false
          ports:
            - name: main
              containerPort: 8080
          env:
            - name: "KUBERNETES_NAMESPACE"
              valueFrom:
                fieldRef:
                  fieldPath: "metadata.namespace"
      restartPolicy: Always
      terminationGracePeriodSeconds: 30
      securityContext: { }
      schedulerName: default-scheduler
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxUnavailable: 25%
      maxSurge: 25%
  revisionHistoryLimit: 10
  progressDeadlineSeconds: 600
{{- end }}

{{- define "sandbox-chart.service" }}
apiVersion: v1
kind: Service
metadata:
  name: {{.Values.ComponentName}}
spec:
  selector:
    k8s-app: {{.Values.ComponentName}}
  ports:
    - protocol: TCP
      port: 8080
      targetPort: 8080
{{- end }}